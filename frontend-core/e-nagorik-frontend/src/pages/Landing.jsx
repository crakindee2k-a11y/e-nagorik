import { Link } from "react-router-dom";
import { SERVICES } from "../data/mockData.js";

export default function Landing()
{
    return (
        <div>
            <div style={{
                background: "linear-gradient(150deg, #006A4E 0%, #003d2b 55%, #1a2035 100%)",
                color: "white", padding: "70px 40px 60px", textAlign: "center",
            }}>
                <div style={{ fontSize: 54, marginBottom: 6 }}>🇧🇩</div>
                <h1 style={{ fontSize: 40, fontWeight: 900, margin: "0 0 4px" }}>ই-নাগরিক</h1>
                <h2 style={{ fontSize: 20, fontWeight: 400, margin: "0 0 10px", opacity: .9 }}>
                    E-Nagorik — Digital City Services
                </h2>
                <p style={{ fontSize: 15, opacity: .8, maxWidth: 520, margin: "0 auto 28px", lineHeight: 1.7 }}>
                    Apply for services, track applications, and receive QR-verified certificates — fully online, fully transparent.
                </p>
                <div style={{ display: "flex", gap: 12, justifyContent: "center", flexWrap: "wrap" }}>
                    <Link to="/citizen" className="btn" style={{ background: "#F42A41", color: "white", borderColor: "#F42A41" }}>Citizen Portal</Link>
                    <Link to="/officer" className="btn" style={{ background: "white", color: "#006A4E" }}>Officer Portal</Link>
                    <Link to="/mayor" className="btn" style={{ background: "rgba(255,255,255,.15)", color: "white", borderColor: "rgba(255,255,255,.4)" }}>Mayor Portal</Link>
                </div>
            </div>

            <div style={{ padding: "40px", background: "#f8faf8" }}>
                <h2 style={{ textAlign: "center", color: "#006A4E", marginBottom: 24 }}>Our Services</h2>
                <div className="grid grid-cards" style={{ maxWidth: 880, margin: "0 auto" }}>
                    {SERVICES.map(s => (
                        <Link
                            key={s.id}
                            to={`/citizen/apply/${s.id}`}
                            className="card"
                            style={{ textDecoration: "none", color: "inherit", textAlign: "center", borderTop: "3px solid #006A4E" }}
                        >
                            <div style={{ fontSize: 28, marginBottom: 6 }}>{s.icon}</div>
                            <div style={{ fontWeight: 600, fontSize: 13 }}>{s.name}</div>
                            <div style={{ fontSize: 11, color: "#888" }}>{s.bn}</div>
                        </Link>
                    ))}
                </div>
            </div>
        </div>
    );
}