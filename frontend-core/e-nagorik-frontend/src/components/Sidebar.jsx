import { NavLink } from "react-router-dom";

export default function Sidebar({ items })
{
    return (
        <div className="sidebar">
            {items.map(item => (
                <NavLink
                    key={item.to}
                    to={item.to}
                    end={item.end}
                    className={({ isActive }) => `sidebar-item${isActive ? " active" : ""}`}
                >
                    <span style={{ fontSize: 16 }}>{item.icon}</span>
                    {item.label}
                    {item.badge ? (
                        <span className="badge" style={{ background: "#F42A41", color: "white", marginLeft: "auto" }}>
                            {item.badge}
                        </span>
                    ) : null}
                </NavLink>
            ))}
        </div>
    );
}