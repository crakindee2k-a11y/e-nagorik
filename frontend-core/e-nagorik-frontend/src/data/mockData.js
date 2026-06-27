/**
 * Mirrors com.enagorik.singleton.ServiceCatalog so frontend and
 * backend never disagree on what services or fixed fees exist.
 */
export const SERVICES = [
    { id: 1, name: "Birth Certificate", bn: "জন্ম নিবন্ধন", category: "Civil", fee: 200, slaDays: 7, icon: "📋" },
    { id: 2, name: "Trade License", bn: "ট্রেড লাইসেন্স", category: "Business", fee: 1500, slaDays: 14, icon: "🏪" },
    { id: 3, name: "Holding Tax", bn: "হোল্ডিং ট্যাক্স", category: "Tax", fee: 500, slaDays: 5, icon: "🏠" },
    { id: 4, name: "Death Certificate", bn: "মৃত্যু নিবন্ধন", category: "Civil", fee: 200, slaDays: 7, icon: "📄" },
    { id: 5, name: "Road Complaint", bn: "রাস্তার অভিযোগ", category: "Complaint", fee: 0, slaDays: 3, icon: "🛣️" },
];

export const SEED_APPLICATIONS = [
    { id: "EN-2026-00142", serviceId: 1, citizen: "Mohammad Riyad", nid: "1990123456789", status: "UNDER_REVIEW", date: "2026-06-01", fee: 200, slaDays: 7, officer: "Nazmul Islam" },
    { id: "EN-2026-00138", serviceId: 2, citizen: "Fatema Begum", nid: "1985987654321", status: "VERIFIED", date: "2026-05-28", fee: 1500, slaDays: 14, officer: "Deen Islam" },
    { id: "EN-2026-00131", serviceId: 3, citizen: "Karim Uddin", nid: "1978456123789", status: "PENDING_PAYMENT", date: "2026-05-25", fee: 500, slaDays: 5, officer: null },
    { id: "EN-2026-00125", serviceId: 5, citizen: "Rina Akter", nid: "1992321654987", status: "APPROVED", date: "2026-05-20", fee: 0, slaDays: 3, officer: "Masukur Rahman" },
    { id: "EN-2026-00119", serviceId: 4, citizen: "Salam Sheikh", nid: "1988654321123", status: "REJECTED", date: "2026-05-15", fee: 200, slaDays: 7, officer: "AKM Tanvir" },
];

/** Same labels/states as the Java ApplicationStatus enum. */
export const STATUS_LABEL = {
    SUBMITTED: "Submitted",
    PENDING_PAYMENT: "Pending Payment",
    UNDER_REVIEW: "Under Review",
    VERIFIED: "Verified",
    APPROVED: "Approved",
    REJECTED: "Rejected",
    CORRECTION_REQUESTED: "Correction Requested",
};

export const STATUS_COLOR = {
    SUBMITTED: "#0070C0",
    PENDING_PAYMENT: "#c87000",
    UNDER_REVIEW: "#0070C0",
    VERIFIED: "#C8920A",
    APPROVED: "#006A4E",
    REJECTED: "#F42A41",
    CORRECTION_REQUESTED: "#9C27B0",
};