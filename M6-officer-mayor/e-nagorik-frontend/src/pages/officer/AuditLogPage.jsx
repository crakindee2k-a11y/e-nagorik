const LOG = [
    { time: "2026-06-02 14:32", action: "Application Verified", ref: "EN-2026-00138", by: "Deen Islam" },
    { time: "2026-06-02 11:15", action: "Document Review Started", ref: "EN-2026-00142", by: "Nazmul Islam" },
    { time: "2026-06-01 16:44", action: "Application Rejected", ref: "EN-2026-00119", by: "AKM Tanvir" },
    { time: "2026-06-01 09:20", action: "Approved by Mayor", ref: "EN-2026-00125", by: "Chairman" },
];

export default function AuditLogPage()
{
    return (
        <div>
            <h2 style={{ color: "#006A4E" }}>Audit log</h2>
            <div className="card">
                {LOG.map((entry, i) => (
                    <div key={i} style={{ padding: "10px 0", borderBottom: "1px solid #f5f5f5" }}>
                        <b>{entry.action}</b> — <span style={{ color: "#0070C0" }}>{entry.ref}</span>
                        <div style={{ fontSize: 11, color: "#999" }}>By {entry.by} · {entry.time}</div>
                    </div>
                ))}
            </div>
        </div>
    );
}
