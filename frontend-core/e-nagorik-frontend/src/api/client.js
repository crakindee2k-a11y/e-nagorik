import axios from "axios";

const BASE_URL = import.meta.env.VITE_API_BASE_URL || "http://localhost:8080/api";
export const MOCK_MODE = import.meta.env.VITE_MOCK_MODE !== "false";

const http = axios.create({ baseURL: BASE_URL, timeout: 5000 });

/**
 * Thin wrapper around the Java backend's ENagorikFacade, exposed over
 * REST. Every function name matches a Facade method 1:1 so the
 * frontend/backend mapping stays obvious. Pages should never call
 * axios directly — only through this module (DIP: pages depend on
 * this abstraction, not on the transport).
 */
export const api = {
    listServices: () => http.get("/services").then(r => r.data),
    applyForService: (citizenId, serviceId) => http.post("/applications", { citizenId, serviceId }).then(r => r.data),
    payForApplication: (applicationNo, provider, mobile) => http.post(`/applications/${applicationNo}/pay`, { provider, mobile }).then(r => r.data),
    trackApplication: (applicationNo) => http.get(`/applications/${applicationNo}`).then(r => r.data),
    verifyApplication: (applicationNo, officerId) => http.post(`/applications/${applicationNo}/verify`, { officerId }).then(r => r.data),
    rejectApplication: (applicationNo, officerId, reason) => http.post(`/applications/${applicationNo}/reject`, { officerId, reason }).then(r => r.data),
    requestMayorOtp: (applicationNo) => http.post(`/applications/${applicationNo}/request-otp`).then(r => r.data),
    approveApplication: (applicationNo, mayorId, otp) => http.post(`/applications/${applicationNo}/approve`, { mayorId, otp }).then(r => r.data),
};