import { createContext, useContext, useState } from "react";
import { SEED_APPLICATIONS } from "../data/mockData.js";

const AppContext = createContext(null);

/**
 * Single shared state container — every page reads/writes applications
 * through here instead of duplicating fetch + state logic per page
 * (same DRY motivation as AbstractFileRepository on the backend).
 */
export function AppProvider({ children })
{
    const [applications, setApplications] = useState(SEED_APPLICATIONS);
    const [notifications, setNotifications] = useState([
        { id: 1, message: "Birth Certificate (EN-2026-00142) is under review.", time: "2h ago", read: false },
        { id: 2, message: "Payment confirmed for Trade License (EN-2026-00138).", time: "1d ago", read: true },
    ]);

    function updateStatus(applicationNo, status, extra = {})
    {
        setApplications(prev =>
            prev.map(a => (a.id === applicationNo ? { ...a, status, ...extra } : a))
        );
    }

    function submitApplication(newApp)
    {
        setApplications(prev => [newApp, ...prev]);
    }

    const value = { applications, notifications, updateStatus, submitApplication };
    return <AppContext.Provider value={value}>{children}</AppContext.Provider>;
}

export function useAppData()
{
    const ctx = useContext(AppContext);

    if (!ctx)
    {
        throw new Error("useAppData must be used inside AppProvider");
    }

    return ctx;
}