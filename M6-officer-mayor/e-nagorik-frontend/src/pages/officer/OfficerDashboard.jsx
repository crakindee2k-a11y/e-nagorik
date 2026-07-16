import { Link } from "react-router-dom";
import { useAppData } from "../../context/AppContext.jsx";
import StatCard from "../../components/StatCard.jsx";
import StatusBadge from "../../components/StatusBadge.jsx";

export default function OfficerDashboard()
{
    const { applications } = useAppData();
    const pending = applications.filter(a => ["PENDING_PAYMENT", "UNDER_REVIEW"].includes(a.status));

    return (
        <div>
            <h2 style={{ color: "#006A4E" }}>Officer dashboard</h2>
            <p style={{ color: "#666", marginTop: -8, fontSize: 13 }}>Nazmul Islam · Helpdesk Officer</p>

            <div className="grid grid-stats" style={{ marginBottom: 20 }}>
                <StatCard color="#0070C0" value={applications.length} label="Total apps" icon="📋" />
                <StatCard color="#C8920A" value={pending.length} label="Pending review" icon="🔍" />
                <StatCard color="#006A4E" value={applications.filter(a => a.status === "VERIFIED").length} label="Verified" icon="✅" />
                <StatCard color="#F42A41" value={0} label="SLA breach" icon="⚠️" />
            </div>

            <div className="card">
                <h3 style={{ color: "#006A4E" }}>Pending actions</h3>
                {pending.map(a => (
                    <div key={a.id} style={{ display: "flex", justifyContent: "space-between", padding: "10px 0", borderBottom: "1px solid #f0f0f0" }}>
                        <div><b style={{ color: "#0070C0" }}>{a.id}</b> · {a.citizen}</div>
                        <div style={{ display: "flex", gap: 8, alignItems: "center" }}>
                            <StatusBadge status={a.status} />
                            <Link to="/officer/queue" className="btn btn-primary">Review</Link>
                        </div>
                    </div>
                ))}
                {pending.length === 0 && <p style={{ color: "#888" }}>No pending actions.</p>}
            </div>
        </div>
    );
}