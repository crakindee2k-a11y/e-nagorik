import { useAppData } from "../../context/AppContext.jsx";
import StatusBadge from "../../components/StatusBadge.jsx";

function daysLeft(app)
{
    const submitted = new Date(app.date);
    const elapsed = Math.floor((Date.now() - submitted.getTime()) / (1000 * 60 * 60 * 24));
    return app.slaDays - elapsed;
}

export default function SlaPage()
{
    const { applications } = useAppData();

    return (
        <div>
            <h2 style={{ color: "#006A4E" }}>SLA monitor</h2>
            {applications.map(a => {
                const left = daysLeft(a);
                const color = left <= 0 ? "#F42A41" : left <= 1 ? "#C8920A" : "#006A4E";

                return (
                    <div key={a.id} className="card" style={{ borderLeft: `4px solid ${color}`, display: "flex", justifyContent: "space-between" }}>
                        <div><b style={{ color: "#0070C0" }}>{a.id}</b> · {a.citizen}</div>
                        <div style={{ display: "flex", gap: 8, alignItems: "center" }}>
                            <StatusBadge status={a.status} />
                            <b style={{ color }}>{left}d left</b>
                        </div>
                    </div>
                );
            })}
        </div>
    );
}