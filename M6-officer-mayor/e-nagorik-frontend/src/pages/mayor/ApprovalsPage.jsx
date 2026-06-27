import { useState } from "react";
import { useAppData } from "../../context/AppContext.jsx";
import { api, MOCK_MODE } from "../../api/client.js";

const DEMO_OTP = "123456";
const DEMO_MAYOR_ID = 1;

export default function ApprovalsPage()
{
    const { applications, updateStatus } = useAppData();
    const verified = applications.filter(a => a.status === "VERIFIED");

    const [activeId, setActiveId] = useState(null);
    const [otpSent, setOtpSent] = useState(false);
    const [otpValue, setOtpValue] = useState("");
    const [error, setError] = useState(null);

    async function sendOtp(appNo)
    {
        setActiveId(appNo);
        setOtpSent(false);
        setOtpValue("");
        setError(null);

        if (!MOCK_MODE)
        {
            await api.requestMayorOtp(appNo);
        }
        setOtpSent(true);
    }

    async function confirmApproval(appNo)
    {
        if (MOCK_MODE && otpValue !== DEMO_OTP)
        {
            setError(`Invalid OTP. Demo OTP is ${DEMO_OTP}.`);
            return;
        }

        if (!MOCK_MODE)
        {
            await api.approveApplication(appNo, DEMO_MAYOR_ID, otpValue);
        }

        updateStatus(appNo, "APPROVED");
        setActiveId(null);
    }

    return (
        <div style={{ maxWidth: 640, margin: "0 auto" }}>
            <h2 style={{ color: "#006A4E" }}>🔐 OTP-secured approval</h2>
            {verified.length === 0 && <div className="card" style={{ textAlign: "center", color: "#888" }}>No pending approvals.</div>}

            {verified.map(a => (
                <div key={a.id} className="card" style={{ borderTop: "3px solid #006A4E" }}>
                    <div>
                        <div style={{ fontWeight: 700, color: "#006A4E" }}>{a.id}</div>
                        <div style={{ color: "#666", fontSize: 13 }}>{a.citizen} · Officer: {a.officer}</div>
                    </div>

                    {activeId !== a.id ? (
                        <button className="btn btn-primary btn-block" style={{ marginTop: 12 }} onClick={() => sendOtp(a.id)}>
                            🔐 Approve with OTP
                        </button>
                    ) : (
                        <div style={{ background: "#006A4E08", borderRadius: 8, padding: 16, marginTop: 12 }}>
                            {otpSent ? (
                                <>
                                    <p style={{ fontSize: 12, color: "#006A4E" }}>✅ OTP sent to mayor's mobile (demo OTP: {DEMO_OTP})</p>
                                    <input
                                        placeholder="------"
                                        maxLength={6}
                                        value={otpValue}
                                        onChange={e => setOtpValue(e.target.value)}
                                        style={{ textAlign: "center", fontSize: 20, letterSpacing: 8, marginBottom: 10 }}
                                    />
                                    {error && <p style={{ color: "#F42A41", fontSize: 12 }}>{error}</p>}
                                    <div style={{ display: "flex", gap: 8 }}>
                                        <button className="btn" onClick={() => setActiveId(null)}>Cancel</button>
                                        <button className="btn btn-primary" style={{ flex: 1 }} onClick={() => confirmApproval(a.id)}>✅ Confirm approval</button>
                                    </div>
                                </>
                            ) : (
                                <p>Sending OTP…</p>
                            )}
                        </div>
                    )}
                </div>
            ))}
        </div>
    );
}