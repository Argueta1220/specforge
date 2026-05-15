import { useEffect, useMemo, useState } from 'react';

const emptyParts = {
  cpus: [],
  motherboards: [],
  ramModules: [],
  psus: []
};

const initialSelection = {
  cpuId: '',
  motherboardId: '',
  ramId: '',
  psuId: ''
};

function App() {
  const [parts, setParts] = useState(emptyParts);
  const [selection, setSelection] = useState(initialSelection);
  const [result, setResult] = useState(null);
  const [loadingParts, setLoadingParts] = useState(true);
  const [checking, setChecking] = useState(false);
  const [message, setMessage] = useState('');

  useEffect(() => {
    async function loadParts() {
      try {
        const response = await fetch('/api/parts');

        if (!response.ok) {
          throw new Error('Could not load parts.');
        }

        const data = await response.json();
        setParts(data);
        setSelection({
          cpuId: String(data.cpus[0]?.id ?? ''),
          motherboardId: String(data.motherboards[0]?.id ?? ''),
          ramId: String(data.ramModules[0]?.id ?? ''),
          psuId: String(data.psus[0]?.id ?? '')
        });
      } catch (error) {
        setMessage(error.message);
      } finally {
        setLoadingParts(false);
      }
    }

    loadParts();
  }, []);

  const selectedParts = useMemo(() => ({
    cpu: findById(parts.cpus, selection.cpuId),
    motherboard: findById(parts.motherboards, selection.motherboardId),
    ram: findById(parts.ramModules, selection.ramId),
    psu: findById(parts.psus, selection.psuId)
  }), [parts, selection]);
  const canCheck = Object.values(selection).every(Boolean);

  function updateSelection(event) {
    const { name, value } = event.target;
    setSelection((currentSelection) => ({
      ...currentSelection,
      [name]: value
    }));
    setResult(null);
    setMessage('');
  }

  async function checkCompatibility(event) {
    event.preventDefault();
    setChecking(true);
    setMessage('');

    try {
      const response = await fetch('/api/compatibility/check', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify(toNumberSelection(selection))
      });

      const data = await response.json();

      if (!response.ok) {
        throw new Error(data.error ?? 'Could not check compatibility.');
      }

      setResult(data);
    } catch (error) {
      setResult(null);
      setMessage(error.message);
    } finally {
      setChecking(false);
    }
  }

  return (
    <main className="app-shell">
      <header className="page-header">
        <div>
          <p className="eyebrow">Build planner</p>
          <h1>SpecForge</h1>
        </div>
        <span className="api-pill">Spring Boot + React</span>
      </header>

      <section className="workspace" aria-label="Compatibility checker">
        <form className="selector-panel" onSubmit={checkCompatibility}>
          <div className="panel-heading">
            <h2>Parts</h2>
            {loadingParts && <span className="muted">Loading</span>}
          </div>

          <PartSelect
            label="CPU"
            name="cpuId"
            value={selection.cpuId}
            parts={parts.cpus}
            onChange={updateSelection}
            detail={selectedParts.cpu && `${selectedParts.cpu.socket} socket, ${selectedParts.cpu.tdpWatts}W TDP`}
          />

          <PartSelect
            label="Motherboard"
            name="motherboardId"
            value={selection.motherboardId}
            parts={parts.motherboards}
            onChange={updateSelection}
            detail={selectedParts.motherboard && `${selectedParts.motherboard.socket} socket, ${selectedParts.motherboard.ramType} RAM`}
          />

          <PartSelect
            label="RAM"
            name="ramId"
            value={selection.ramId}
            parts={parts.ramModules}
            onChange={updateSelection}
            detail={selectedParts.ram && `${selectedParts.ram.capacityGb}GB, ${selectedParts.ram.type}, ${selectedParts.ram.wattageWatts}W`}
          />

          <PartSelect
            label="PSU"
            name="psuId"
            value={selection.psuId}
            parts={parts.psus}
            onChange={updateSelection}
            detail={selectedParts.psu && `${selectedParts.psu.wattageWatts}W capacity`}
          />

          <button className="primary-button" disabled={loadingParts || checking || !canCheck}>
            {checking ? 'Checking...' : 'Check Compatibility'}
          </button>
        </form>

        <ResultsPanel result={result} message={message} />
      </section>
    </main>
  );
}

function PartSelect({ label, name, value, parts, onChange, detail }) {
  return (
    <label className="field">
      <span>{label}</span>
      <select name={name} value={value} onChange={onChange}>
        {parts.map((part) => (
          <option key={part.id} value={part.id}>
            {part.name}
          </option>
        ))}
      </select>
      {detail && <small>{detail}</small>}
    </label>
  );
}

function ResultsPanel({ result, message }) {
  const statusClass = result?.compatible ? 'status-good' : 'status-bad';
  const statusText = result ? (result.compatible ? 'Compatible' : 'Needs Changes') : 'Not Checked';

  return (
    <section className="results-panel" aria-label="Compatibility results">
      <div className="panel-heading">
        <h2>Results</h2>
        <span className={`status-badge ${result ? statusClass : ''}`}>{statusText}</span>
      </div>

      {message && <p className="message">{message}</p>}

      <div className="wattage-box">
        <span>Estimated wattage</span>
        <strong>{result ? `${result.estimatedWattage}W` : '--'}</strong>
      </div>

      <ResultList title="Errors" items={result?.errors ?? []} emptyText="No errors." />
      <ResultList title="Warnings" items={result?.warnings ?? []} emptyText="No warnings." />
    </section>
  );
}

function ResultList({ title, items, emptyText }) {
  return (
    <div className="result-group">
      <h3>{title}</h3>
      {items.length > 0 ? (
        <ul>
          {items.map((item) => (
            <li key={item}>{item}</li>
          ))}
        </ul>
      ) : (
        <p className="muted">{emptyText}</p>
      )}
    </div>
  );
}

function findById(parts, id) {
  return parts.find((part) => String(part.id) === String(id));
}

function toNumberSelection(selection) {
  return {
    cpuId: Number(selection.cpuId),
    motherboardId: Number(selection.motherboardId),
    ramId: Number(selection.ramId),
    psuId: Number(selection.psuId)
  };
}

export default App;
