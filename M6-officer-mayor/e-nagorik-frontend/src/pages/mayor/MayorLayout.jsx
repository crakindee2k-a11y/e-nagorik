import { Outlet } from "react-router-dom";
import Sidebar from "../../components/Sidebar.jsx";
import { useAppData } from "../../context/AppContext.jsx";

export default function MayorLayout()
{
    const { applications } = useAppData();
    const pending = applications.filter(a => a.status === "VERIFIED").length;

    const items = [
        { to: "/mayor", end: true, icon: "🏛️", label: "Dashboard" },
        { to: "/mayor/approvals", icon: "✅", label: "Pending Approvals", badge: pending || undefined },
        { to: "/mayor/analytics", icon: "📊", label: "Analytics" },
    ];

    return (
        <div className="layout">
            <Sidebar items={items} />
            <div className="main"><Outlet /></div>
        </div>
    );
}