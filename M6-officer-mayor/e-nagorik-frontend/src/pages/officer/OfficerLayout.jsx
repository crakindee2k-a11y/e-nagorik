import { Outlet } from "react-router-dom";
import Sidebar from "../../components/Sidebar.jsx";
import { useAppData } from "../../context/AppContext.jsx";

export default function OfficerLayout()
{
    const { applications } = useAppData();
    const pendingCount = applications.filter(a => ["PENDING_PAYMENT", "UNDER_REVIEW"].includes(a.status)).length;

    const items = [
        { to: "/officer", end: true, icon: "🏠", label: "Dashboard" },
        { to: "/officer/queue", icon: "📋", label: "Application Queue", badge: pendingCount || undefined },
        { to: "/officer/audit", icon: "📝", label: "Audit Log" },
        { to: "/officer/sla", icon: "⏰", label: "SLA Monitor" },
    ];

    return (
        <div className="layout">
            <Sidebar items={items} />
            <div className="main"><Outlet /></div>
        </div>
    );
}