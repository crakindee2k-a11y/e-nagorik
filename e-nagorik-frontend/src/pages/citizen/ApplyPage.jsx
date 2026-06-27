import { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import { SERVICES } from "../../data/mockData.js";
import { useAppData } from "../../context/AppContext.jsx";
import { api, MOCK_MODE } from "../../api/client.js";

const STEPS = ["Service Info", "Personal Info", "Documents", "Payment", "Done"];
const DEMO_CITIZEN_ID = 1;

export default function ApplyPage()
{
    const { serviceId } = useParams();
    const navigate = useNavigate();
    const { submitApplication } = useAppData();
    const service = SERVICES.find(s => s.id === Number(serviceId));

    const [step, setStep] = useState(1);
    const [provider, setProvider] = useState("bkash");
    const [appNo, setAppNo] = useState(null);
    const [error, setError] = useState(null);

    if (!service)
    {
        return (
            <div>
                <p>Service not found.</p>
                <button className="btn btn-outline" onClick={() => navigate("/citizen/services")}>Back to services</button>
            </div>
        );
    }

    async function handleSubmit()
    {
        setError(null);

        try
        {
            const newAppNo = MOCK_MODE
                ? `EN-2026-${String(Math.floor(Math.random() * 90000) + 10000)}`
                : await submitToBackend();

            submitApplication({
                id: newAppNo,
                serviceId: service.id,
                citizen: "Mohammad Riyad",
                nid: "1990123456789",
                status: "UNDER_REVIEW",
                date: new Date().toISOString().slice(0, 10),
                fee: service.fee,
                slaDays: service.slaDays,
                officer: null,
            });

            setAppNo(newAppNo);
            setStep(5);
        }
        catch (e)
        {
            setError("Could not submit application. Please try again.");
        }
    }

    async function submitToBackend()
    {
        const result = await api.applyForService(DEMO_CITIZEN_ID, service.id);
        await api.payForApplication(result.applicationNo, provider, "01700000000");
        return result.applicationNo;
    }

    if (step === 5)
    {
        return (
            <div className="card" style={{ maxWidth: 480, margin: "0 auto", textAlign: "center" }}>
                <div style={{ fontSize: 40, marginBottom: 10 }}>✅</div>
                <h2 style={{ color: "#006A4E" }}>Application submitted!</h2>
                <p style={{ color: "#666" }}>আপনার আবেদন সফলভাবে জমা হয়েছে</p>
                <div style={{ background: "#006A4E10", borderRadius: 8, padding: "12px 20px", margin: "12px 0" }}>
                    <div style={{ fontSize: 12, color: "#666" }}>Application ID</div>
                    <div style={{ fontSize: 20, fontWeight: 800, color: "#006A4E" }}>{appNo}</div>
                </div>
                <button className="btn btn-primary" onClick={() => navigate("/citizen/track")}>Track application</button>
            </div>
        );
    }

    return (
        <div style={{ maxWidth: 640, margin: "0 auto" }}>
            <h2 style={{ color: "#006A4E" }}>Apply: {service.name}</h2>

            <div className="stepper">
                {STEPS.map((s, i) => (
                    <div key={s} style={{ display: "flex", alignItems: "center", flex: i < STEPS.length - 1 ? 1 : "none" }}>
                        <div className={`step-circle ${step > i + 1 ? "done" : step === i + 1 ? "current" : ""}`}>
                            {step > i + 1 ? "✓" : i + 1}
                        </div>
                        {i < STEPS.length - 1 && <div className={`step-line ${step > i + 1 ? "done" : ""}`} />}
                    </div>
                ))}
            </div>

            <div className="card">
                {step === 1 && (
                    <>
                        <div style={{ display: "flex", gap: 14, alignItems: "center", marginBottom: 16 }}>
                            <span style={{ fontSize: 36 }}>{service.icon}</span>
                            <div>
                                <div style={{ fontWeight: 700, fontSize: 17 }}>{service.name}</div>
                                <div style={{ color: "#666" }}>{service.bn}</div>
                            </div>
                        </div>
                        <p>Fee: ৳{service.fee} · SLA: {service.slaDays} days</p>
                        <button className="btn btn-primary btn-block" onClick={() => setStep(2)}>Continue →</button>
                    </>
                )}

                {step === 2 && (
                    <>
                        <h3 style={{ color: "#006A4E" }}>Personal information</h3>
                        <div className="grid" style={{ gridTemplateColumns: "1fr 1fr", marginBottom: 14 }}>
                            <input placeholder="Full name" defaultValue="Mohammad Riyad" />
                            <input placeholder="NID number" defaultValue="1990123456789" />
                            <input placeholder="Phone" defaultValue="01712345678" />
                            <input placeholder="Email" defaultValue="riyad@mail.com" />
                        </div>
                        <button className="btn btn-primary btn-block" onClick={() => setStep(3)}>Save & continue →</button>
                    </>
                )}

                {step === 3 && (
                    <>
                        <h3 style={{ color: "#006A4E" }}>Upload documents</h3>
                        {["National ID Card", "Photograph", "Proof of Address"].map(d => (
                            <div key={d} style={{ border: "2px dashed #ccc", borderRadius: 8, padding: 14, marginBottom: 10, textAlign: "center" }}>
                                📎 {d}
                            </div>
                        ))}
                        <button className="btn btn-primary btn-block" onClick={() => setStep(4)}>Continue to payment →</button>
                    </>
                )}

                {step === 4 && (
                    <>
                        <h3 style={{ color: "#006A4E" }}>Payment</h3>
                        <p>Total due: <b>৳{service.fee}</b></p>
                        {service.fee > 0 && (
                            <div style={{ display: "flex", gap: 12, marginBottom: 16 }}>
                                {["bkash", "nagad"].map(p => (
                                    <div
                                        key={p}
                                        onClick={() => setProvider(p)}
                                        className="card"
                                        style={{ flex: 1, textAlign: "center", cursor: "pointer", border: provider === p ? "2px solid #006A4E" : "1px solid #ddd" }}
                                    >
                                        {p === "bkash" ? "📱 bKash" : "📲 Nagad"}
                                    </div>
                                ))}
                            </div>
                        )}
                        {error && <p style={{ color: "#F42A41" }}>{error}</p>}
                        <button className="btn btn-primary btn-block" onClick={handleSubmit}>
                            {service.fee === 0 ? "Submit application ✓" : `Pay ৳${service.fee} & submit →`}
                        </button>
                    </>
                )}
            </div>
        </div>
    );
}