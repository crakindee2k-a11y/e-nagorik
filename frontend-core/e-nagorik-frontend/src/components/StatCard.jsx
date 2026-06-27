export default function StatCard({ color, value, label, icon })
{
    return (
        <div className="stat-card" style={{ background: color }}>
            <div style={{ fontSize: 22, marginBottom: 4 }}>{icon}</div>
            <div className="value">{value}</div>
            <div className="label">{label}</div>
        </div>
    );
}