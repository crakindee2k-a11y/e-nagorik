import { useAppData } from "../../context/AppContext.jsx";

export default function NotificationsPage()
{
    const { notifications } = useAppData();

    return (
        <div style={{ maxWidth: 560 }}>
            <h2 style={{ color: "#006A4E" }}>বিজ্ঞপ্তি — Notifications</h2>
            {notifications.map(n => (
                <div
                    key={n.id}
                    className="card"
                    style={{ borderLeft: `4px solid ${n.read ? "#ddd" : "#006A4E"}`, opacity: n.read ? .75 : 1 }}
                >
                    <div style={{ fontWeight: n.read ? 400 : 600, fontSize: 13 }}>{n.message}</div>
                    <div style={{ fontSize: 11, color: "#999" }}>{n.time}</div>
                </div>
            ))}
        </div>
    );
}