import { useState } from "react";
import { useAppData } from "../../context/AppContext.jsx";
import StatusBadge from "../../components/StatusBadge.jsx";
import { api, MOCK_MODE } from "../../api/client.js";

const DEMO_OFFICER_ID = 99;

export default function QueuePage()
{
    const { applications, updateStatus } = useAppData();
    const [reviewing, setReviewing] = useState(null);

    async function decide(decision)
    {
        const appNo = reviewing.id;

        if (!MOCK_MODE)
        {
            if (decision === "VERIFIED") await api.verifyApplication(appNo, DEMO_OFFICER_ID);
            if (decision === "REJECTED") await api.rejectApplication(appNo, DEMO_OFFICER_ID, "Documents incomplete");
        }

        updateStatus(appNo, decision, { officer: "Nazmul Islam" });
        setReviewing(null);
    }

    return (
        <div>
            <h2 style={{ color: "#006A4E" }}>Application queue</h2>
            <div className="card">
                <table>
                    <thead>
                        <tr><th>App. ID</th><th>Citizen</th><th>Date</th><th>Status</th><th>Action</th></tr>
                    </thead>
                    <tbody>
                        {applications.map(a => (
                            <tr key={a.id}>
                                <td style={{ color: "#0070C0", fontWeight: 600 }}>{a.id}</td>
                                <td>{a.citizen}</td>
                                <td style={{ color: "#888" }}>{a.date}</td>
                                <td><StatusBadge status={a.status} /></td>
                                <td>
                                    {["PENDING_PAYMENT", "UNDER_REVIEW"].includes(a.status) && (
                                        <button className="btn btn-primary" onClick={() => setReviewing(a)}>Review</button>
                                    )}
                                </td>
                            </tr>
                        ))}
                    </tbody>
                </table>
            </div>

            {reviewing && (
                <div style={{ position: "fixed", inset: 0, background: "rgba(0,0,0,.48)", display: "flex", alignItems: "center", justifyContent: "center", zIndex: 500 }}>
                    <div className="card" style={{ width: 420 }}>
                        <h3 style={{ color: "#006A4E" }}>Review {reviewing.id}</h3>
                        <p>{reviewing.citizen} · NID: {reviewing.nid}</p>
                        <div style={{ display: "flex", gap: 8, marginTop: 16 }}>
                            <button className="btn btn-danger" onClick={() => decide("REJECTED")}>❌ Reject</button>
                            <button className="btn btn-outline" onClick={() => decide("CORRECTION_REQUESTED")}>✏️ Correction</button>
                            <button className="btn btn-primary" onClick={() => decide("VERIFIED")}>✅ Verify</button>
                        </div>
                        <button className="btn" style={{ marginTop: 10 }} onClick={() => setReviewing(null)}>Cancel</button>
                    </div>
                </div>
            )}
        </div>
    );
}