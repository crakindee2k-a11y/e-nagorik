import { Outlet } from "react-router-dom";
import Sidebar from "../../components/Sidebar.jsx";
import { useAppData } from "../../context/AppContext.jsx";

export default function CitizenLayout()
{
    const { notifications } = useAppData();
    const unread = notifications.filter(n => !n.read).length;

    const items = [
        { to: "/citizen", end: true, icon: "🏠", label: "Dashboard" },
        { to: "/citizen/services", icon: "📋", label: "Services" },
        { to: "/citizen/track", icon: "🔍", label: "Track" },
        { to: "/citizen/certificates", icon: "📜", label: "Certificates" },
        { to: "/citizen/notifications", icon: "🔔", label: "Notifications", badge: unread || undefined },
    ];

    return (
        <div className="layout">
            <Sidebar items={items} />
            <div className="main"><Outlet /></div>
        </div>
    );
}