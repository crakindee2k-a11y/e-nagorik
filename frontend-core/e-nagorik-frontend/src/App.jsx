import { Routes, Route } from "react-router-dom";
import Navbar from "./components/Navbar.jsx";
import Landing from "./pages/Landing.jsx";

import CitizenLayout from "./pages/citizen/CitizenLayout.jsx";
import CitizenDashboard from "./pages/citizen/CitizenDashboard.jsx";
import ServicesPage from "./pages/citizen/ServicesPage.jsx";
import ApplyPage from "./pages/citizen/ApplyPage.jsx";
import TrackPage from "./pages/citizen/TrackPage.jsx";
import CertificatesPage from "./pages/citizen/CertificatesPage.jsx";
import NotificationsPage from "./pages/citizen/NotificationsPage.jsx";

import OfficerLayout from "./pages/officer/OfficerLayout.jsx";
import OfficerDashboard from "./pages/officer/OfficerDashboard.jsx";
import QueuePage from "./pages/officer/QueuePage.jsx";
import AuditLogPage from "./pages/officer/AuditLogPage.jsx";
import SlaPage from "./pages/officer/SlaPage.jsx";

import MayorLayout from "./pages/mayor/MayorLayout.jsx";
import MayorDashboard from "./pages/mayor/MayorDashboard.jsx";
import ApprovalsPage from "./pages/mayor/ApprovalsPage.jsx";
import AnalyticsPage from "./pages/mayor/AnalyticsPage.jsx";

export default function App()
{
    return (
        <>
            <Navbar />
            <Routes>
                <Route path="/" element={<Landing />} />

                <Route path="/citizen" element={<CitizenLayout />}>
                    <Route index element={<CitizenDashboard />} />
                    <Route path="services" element={<ServicesPage />} />
                    <Route path="apply/:serviceId" element={<ApplyPage />} />
                    <Route path="track" element={<TrackPage />} />
                    <Route path="certificates" element={<CertificatesPage />} />
                    <Route path="notifications" element={<NotificationsPage />} />
                </Route>

                <Route path="/officer" element={<OfficerLayout />}>
                    <Route index element={<OfficerDashboard />} />
                    <Route path="queue" element={<QueuePage />} />
                    <Route path="audit" element={<AuditLogPage />} />
                    <Route path="sla" element={<SlaPage />} />
                </Route>

                <Route path="/mayor" element={<MayorLayout />}>
                    <Route index element={<MayorDashboard />} />
                    <Route path="approvals" element={<ApprovalsPage />} />
                    <Route path="analytics" element={<AnalyticsPage />} />
                </Route>
            </Routes>
        </>
    );
}