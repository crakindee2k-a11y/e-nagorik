import { useState } from "react";
import { useAppData } from "../../context/AppContext.jsx";
import StatusBadge from "../../components/StatusBadge.jsx";

const FLOW = ["SUBMITTED", "PENDING_PAYMENT", "UNDER_REVIEW", "VERIFIED", "APPROVED"];

export default function TrackPage()
{
    const { applications } = useAppData();
    const [query, setQuery] = useState("");
    const [result, setResult] = useState(null);

    function handleTrack()
    {
        const found = applications.find(a => a.id === query.trim());
        setResult(found || "not-found");
    }

    function stepIndex(status)
    {
        const idx = FLOW.indexOf(status);
        return idx === -1 ? 2 : idx;
    }

    return (
        <div style={{ maxWidth: 640, margin: "0 auto" }}>
            <h2 style={{ color: "#006A4E" }}>Track application</h2>

            <div className="card">
                <div style={{ display: "flex", gap: 10 }}>
                    <input placeholder="Enter Application ID (e.g. EN-2026-00142)" value={query} onChange={e => setQuery(e.target.value)} />
                    <button className="btn btn-primary" onClick={handleTrack}>🔍 Track</button>
                </div>
                <div style={{ display: "flex", gap: 6, flexWrap: "wrap", marginTop: 10 }}>
                    {applications.slice(0, 4).map(a => (
                        <button
                            key={a.id}
                            className="btn btn-outline"
                            style={{ padding: "3px 9px", fontSize: 11 }}
                            onClick={() => { setQuery(a.id); setResult(a); }}
                        >
                            {a.id}
                        </button>
                    ))}
                </div>
            </div>

            {result === "not-found" && (
                <div className="card" style={{ textAlign: "center", color: "#F42A41" }}>No application found with this ID.</div>
            )}

            {result && result !== "not-found" && (
                <div className="card">
                    <div style={{ display: "flex", justifyContent: "space-between", marginBottom: 16 }}>
                        <div>
                            <div style={{ fontWeight: 700, fontSize: 18, color: "#006A4E" }}>{result.id}</div>
                            <div style={{ color: "#666", fontSize: 13 }}>Submitted: {result.date}</div>
                        </div>
                        <StatusBadge status={result.status} />
                    </div>

                    <div style={{ display: "flex", gap: 4 }}>
                        {FLOW.map((s, i) => (
                            <div
                                key={s}
                                style={{ flex: 1, height: 6, borderRadius: 3, background: i <= stepIndex(result.status) ? "#006A4E" : "#e0e0e0" }}
                            />
                        ))}
                    </div>
                    <p style={{ fontSize: 12, color: "#888", marginTop: 8 }}>
                        Officer: {result.officer || "Not yet assigned"} · Fee: ৳{result.fee} · SLA: {result.slaDays} days
                    </p>
                </div>
            )}
        </div>
    );
}