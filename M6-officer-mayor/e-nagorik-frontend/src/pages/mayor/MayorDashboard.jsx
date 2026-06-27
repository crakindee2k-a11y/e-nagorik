import { Link } from "react-router-dom";
import { useAppData } from "../../context/AppContext.jsx";
import StatCard from "../../components/StatCard.jsx";

export default function MayorDashboard()
{
    const { applications } = useAppData();
    const verified = applications.filter(a => a.status === "VERIFIED");
    const approved = applications.filter(a => a.status === "APPROVED");

    return (
        <div>
            <h2 style={{ color: "#006A4E" }}>🏛️ Mayor's dashboard</h2>

            <div className="grid grid-stats" style={{ marginBottom: 20 }}>
                <StatCard color="#C8920A" value={verified.length} label="Awaiting approval" icon="⏳" />
                <StatCard color="#006A4E" value={approved.length} label="Approved" icon="✅" />
                <StatCard color="#0070C0" value={applications.length} label="Total processed" icon="📋" />
            </div>

            <div className="card">
                <h3 style={{ color: "#006A4E" }}>Verified — awaiting approval</h3>
                {verified.map(a => (
                    <div key={a.id} style={{ display: "flex", justifyContent: "space-between", padding: "10px 0", borderBottom: "1px solid #f0f0f0" }}>
                        <div><b style={{ color: "#0070C0" }}>{a.id}</b> · {a.citizen}</div>
                        <Link to="/mayor/approvals" className="btn btn-primary">Review & approve</Link>
                    </div>
                ))}
                {verified.length === 0 && <p style={{ color: "#888" }}>All verified applications processed.</p>}
            </div>
        </div>
    );
}