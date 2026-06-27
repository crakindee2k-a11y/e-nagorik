import { Link, useLocation } from "react-router-dom";

const PORTALS = [
    { path: "/citizen", label: "👤 Citizen" },
    { path: "/officer", label: "👮 Officer" },
    { path: "/mayor", label: "🏛️ Mayor" },
];

export default function Navbar()
{
    const location = useLocation();

    return (
        <div className="navbar">
            <Link to="/" className="navbar-brand">
                <span style={{ fontSize: 26 }}>🇧🇩</span>
                <div>
                    <div style={{ fontWeight: 800, fontSize: 17, lineHeight: 1 }}>
                        ই-নাগরিক <span style={{ fontWeight: 300, fontSize: 13 }}>E-Nagorik</span>
                    </div>
                    <div style={{ fontSize: 10, opacity: .75 }}>Dhaka City Corporation</div>
                </div>
            </Link>
            <div className="navbar-links">
                {PORTALS.map(p => (
                    <Link
                        key={p.path}
                        to={p.path}
                        className={`navbar-link${location.pathname.startsWith(p.path) ? " active" : ""}`}
                    >
                        {p.label}
                    </Link>
                ))}
            </div>
        </div>
    );
}