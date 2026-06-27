import { useAppData } from "../../context/AppContext.jsx";

export default function CertificatesPage()
{
    const { applications } = useAppData();
    const approved = applications.filter(a => a.status === "APPROVED");

    return (
        <div>
            <h2 style={{ color: "#006A4E" }}>সার্টিফিকেট — Certificates</h2>
            {approved.length === 0 && <p style={{ color: "#888" }}>No approved certificates yet.</p>}
            <div className="grid grid-cards">
                {approved.map(a => (
                    <div key={a.id} className="card" style={{ borderTop: "3px solid #006A4E" }}>
                        <div style={{ fontWeight: 700 }}>{a.id}</div>
                        <div style={{ fontSize: 12, color: "#888", marginBottom: 10 }}>Issued: {a.date} · QR Verified</div>
                        <button className="btn btn-primary btn-block">📥 Download PDF</button>
                    </div>
                ))}
            </div>
        </div>
    );
}