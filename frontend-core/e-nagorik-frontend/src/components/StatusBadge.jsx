import { STATUS_COLOR, STATUS_LABEL } from "../data/mockData.js";

export default function StatusBadge({ status })
{
    const color = STATUS_COLOR[status] || "#888";
    return (
        <span className="badge" style={{ background: `${color}18`, color }}>
            {STATUS_LABEL[status] || status}
        </span>
    );
}