import { Link } from "react-router-dom";
import { useAppData } from "../../context/AppContext.jsx";
import StatCard from "../../components/StatCard.jsx";
import StatusBadge from "../../components/StatusBadge.jsx";

export default function CitizenDashboard()
{
    const { applications } = useAppData();
    const approved = applications.filter(a => a.status === "APPROVED").length;
    const inProgress = applications.filter(a => ["UNDER_REVIEW", "VERIFIED"].includes(a.status)).length;
    const rejected = applications.filter(a => a.status === "REJECTED").length;

    return (
        <div>
            <h2 style={{ color: "#006A4E" }}>স্বাগতম, Mohammad Riyad 👋</h2>
            <p style={{ color: "#666", marginTop: -8, fontSize: 13 }}>NID: 1990123456789 · Dhaka City Corporation</p>

            <div className="grid grid-stats" style={{ marginBottom: 20 }}>
                <StatCard color="#0070C0" value={applications.length} label="Total" icon="📋" />
                <StatCard color="#006A4E" value={approved} label="Approved" icon="✅" />
                <StatCard color="#0070C0" value={inProgress} label="In Progress" icon="🔍" />
                <StatCard color="#F42A41" value={rejected} label="Rejected" icon="❌" />
            </div>

            <div className="card">
                <div style={{ display: "flex", justifyContent: "space-between", alignItems: "center", marginBottom: 14 }}>
                    <h3 style={{ margin: 0, color: "#006A4E" }}>Recent Applications</h3>
                    <Link to="/citizen/track" className="btn btn-primary">View all</Link>
                </div>
                <table>
                    <thead>
                        <tr><th>App. ID</th><th>Citizen</th><th>Date</th><th>Status</th></tr>
                    </thead>
                    <tbody>
                        {applications.slice(0, 4).map(a => (
                            <tr key={a.id}>
                                <td style={{ color: "#0070C0", fontWeight: 600 }}>{a.id}</td>
                                <td>{a.citizen}</td>
                                <td style={{ color: "#888" }}>{a.date}</td>
                                <td><StatusBadge status={a.status} /></td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>

            <div className="grid" style={{ gridTemplateColumns: "repeat(auto-fit, minmax(180px, 1fr))" }}>
                <Link to="/citizen/services" className="card" style={{ textDecoration: "none", color: "#006A4E", fontWeight: 600, borderLeft: "4px solid #006A4E" }}>✏️ Apply for Service</Link>
                <Link to="/citizen/track" className="card" style={{ textDecoration: "none", color: "#0070C0", fontWeight: 600, borderLeft: "4px solid #0070C0" }}>🔍 Track Application</Link>
                <Link to="/citizen/certificates" className="card" style={{ textDecoration: "none", color: "#C8920A", fontWeight: 600, borderLeft: "4px solid #C8920A" }}>📥 My Certificates</Link>
            </div>
        </div>
    );
}