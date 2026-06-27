import { Link } from "react-router-dom";
import { SERVICES } from "../../data/mockData.js";

export default function ServicesPage()
{
    const categories = [...new Set(SERVICES.map(s => s.category))];

    return (
        <div>
            <h2 style={{ color: "#006A4E" }}>সেবাসমূহ — Available Services</h2>
            {categories.map(cat => (
                <div key={cat} style={{ marginBottom: 22 }}>
                    <h3 style={{ fontSize: 14, borderBottom: "2px solid rgba(0,106,78,.15)", paddingBottom: 6 }}>{cat}</h3>
                    <div className="grid grid-cards">
                        {SERVICES.filter(s => s.category === cat).map(s => (
                            <Link
                                key={s.id}
                                to={`/citizen/apply/${s.id}`}
                                className="card"
                                style={{ textDecoration: "none", color: "inherit", display: "flex", gap: 12, alignItems: "center", borderLeft: "3px solid #006A4E" }}
                            >
                                <span style={{ fontSize: 26 }}>{s.icon}</span>
                                <div>
                                    <div style={{ fontWeight: 600, fontSize: 13 }}>{s.name}</div>
                                    <div style={{ fontSize: 11, color: "#888" }}>{s.bn}</div>
                                    <span className="badge" style={{ background: "#006A4E18", color: "#006A4E" }}>
                                        {s.fee > 0 ? `৳${s.fee}` : "Free"}
                                    </span>
                                </div>
                            </Link>
                        ))}
                    </div>
                </div>
            ))}
        </div>
    );
}