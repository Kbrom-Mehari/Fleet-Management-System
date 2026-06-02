"""
Generate a styled Tracking Integration & Logistics Platform Roadmap (.docx)
for the Fleet Management System project.

Requirements
------------
    pip install python-docx

Usage
-----
    python generate_tracking_integration_docx.py

Output
------
    Tracking_Integration_Roadmap.docx  (created in the current working directory)
"""

from docx import Document
from docx.shared import Pt, RGBColor, Cm
from docx.enum.text import WD_ALIGN_PARAGRAPH
from docx.enum.table import WD_ALIGN_VERTICAL
from docx.oxml.ns import qn
from docx.oxml import OxmlElement
import datetime


# ---------------------------------------------------------------------------
# Color palette  (aligned with other docx generators in this repo)
# ---------------------------------------------------------------------------
NAVY = RGBColor(0x1A, 0x37, 0x6C)
TEAL = RGBColor(0x00, 0x7B, 0x83)
WHITE = RGBColor(0xFF, 0xFF, 0xFF)
LIGHT_GREY = RGBColor(0xF2, 0xF2, 0xF2)
DARK_GREY = RGBColor(0x40, 0x40, 0x40)


# ---------------------------------------------------------------------------
# Helper utilities  (styling matches generate_sla_docx.py)
# ---------------------------------------------------------------------------

def set_cell_bg(cell, hex_color: str) -> None:
    """Fill a table cell background with a solid color (hex, e.g. '1A376C')."""
    tc = cell._tc
    tcPr = tc.get_or_add_tcPr()
    shd = OxmlElement("w:shd")
    shd.set(qn("w:val"), "clear")
    shd.set(qn("w:color"), "auto")
    shd.set(qn("w:fill"), hex_color)
    tcPr.append(shd)


def add_horizontal_rule(doc: Document) -> None:
    """Insert a thin teal horizontal rule paragraph."""
    p = doc.add_paragraph()
    pPr = p._p.get_or_add_pPr()
    pBdr = OxmlElement("w:pBdr")
    bottom = OxmlElement("w:bottom")
    bottom.set(qn("w:val"), "single")
    bottom.set(qn("w:sz"), "6")
    bottom.set(qn("w:space"), "1")
    bottom.set(qn("w:color"), "007B83")
    pBdr.append(bottom)
    pPr.append(pBdr)
    p.paragraph_format.space_after = Pt(0)


def add_section_heading(doc: Document, number: str, title: str) -> None:
    """Add a numbered section heading (Heading 1 style, navy background strip)."""
    p = doc.add_paragraph(style="Heading 1")
    run = p.add_run(f"  {number}  {title}")
    run.font.color.rgb = WHITE
    run.font.bold = True
    run.font.size = Pt(13)
    p.paragraph_format.space_before = Pt(14)
    p.paragraph_format.space_after = Pt(6)
    pPr = p._p.get_or_add_pPr()
    shd = OxmlElement("w:shd")
    shd.set(qn("w:val"), "clear")
    shd.set(qn("w:color"), "auto")
    shd.set(qn("w:fill"), "1A376C")
    pPr.append(shd)


def add_sub_heading(doc: Document, title: str) -> None:
    """Add a sub-section heading (Heading 2 style, teal text)."""
    p = doc.add_paragraph(style="Heading 2")
    run = p.add_run(title)
    run.font.color.rgb = TEAL
    run.font.bold = True
    run.font.size = Pt(11)
    p.paragraph_format.space_before = Pt(10)
    p.paragraph_format.space_after = Pt(4)


def add_body(doc: Document, text: str) -> None:
    """Add a normal body paragraph."""
    p = doc.add_paragraph(text, style="Normal")
    p.paragraph_format.space_after = Pt(6)
    for run in p.runs:
        run.font.color.rgb = DARK_GREY
        run.font.size = Pt(10.5)


def add_bullet(doc: Document, bold_label: str, body_text: str) -> None:
    """Add a bullet point with an optional bold label followed by body text."""
    p = doc.add_paragraph(style="List Bullet")
    label_run = p.add_run(bold_label)
    label_run.bold = True
    label_run.font.color.rgb = NAVY
    label_run.font.size = Pt(10.5)
    if body_text:
        body_run = p.add_run(" " + body_text)
        body_run.font.color.rgb = DARK_GREY
        body_run.font.size = Pt(10.5)
    p.paragraph_format.space_after = Pt(4)


def add_styled_table(doc: Document, headers: list, rows: list) -> None:
    """Render a styled data table."""
    num_cols = len(headers)
    table = doc.add_table(rows=1 + len(rows), cols=num_cols)
    table.style = "Table Grid"

    hdr_row = table.rows[0]
    for col_idx, hdr_text in enumerate(headers):
        cell = hdr_row.cells[col_idx]
        set_cell_bg(cell, "1A376C")
        cell.vertical_alignment = WD_ALIGN_VERTICAL.CENTER
        para = cell.paragraphs[0]
        run = para.add_run(hdr_text)
        run.bold = True
        run.font.color.rgb = WHITE
        run.font.size = Pt(10.5)
        run.font.name = "Calibri"
        para.paragraph_format.left_indent = Pt(4)

    for row_idx, row_data in enumerate(rows):
        bg = "E8EEF7" if row_idx % 2 == 0 else "F2F2F2"
        t_row = table.rows[1 + row_idx]
        for col_idx, cell_text in enumerate(row_data):
            cell = t_row.cells[col_idx]
            set_cell_bg(cell, bg)
            cell.vertical_alignment = WD_ALIGN_VERTICAL.CENTER
            para = cell.paragraphs[0]
            run = para.add_run(cell_text)
            run.font.color.rgb = DARK_GREY
            run.font.size = Pt(10.5)
            run.font.name = "Calibri"
            para.paragraph_format.left_indent = Pt(4)

    doc.add_paragraph()


# ---------------------------------------------------------------------------
# Document builder
# ---------------------------------------------------------------------------

def build_document() -> Document:
    doc = Document()

    for section in doc.sections:
        section.top_margin = Cm(2.0)
        section.bottom_margin = Cm(2.0)
        section.left_margin = Cm(2.54)
        section.right_margin = Cm(2.54)

    normal = doc.styles["Normal"]
    normal.font.name = "Calibri"
    normal.font.size = Pt(10.5)
    normal.font.color.rgb = DARK_GREY

    header = doc.sections[0].header
    header_para = header.paragraphs[0]
    header_para.text = (
        "Fleet Management System  |  Tracking Integration & Logistics Platform "
        "Enhancement Plan"
    )
    header_para.alignment = WD_ALIGN_PARAGRAPH.RIGHT
    header_run = header_para.runs[0]
    header_run.font.size = Pt(9)
    header_run.font.color.rgb = TEAL
    header_run.font.italic = True

    footer = doc.sections[0].footer
    footer_para = footer.paragraphs[0]
    footer_para.text = (
        f"Confidential  ·  Generated {datetime.date.today().strftime('%B %d, %Y')}"
    )
    footer_para.alignment = WD_ALIGN_PARAGRAPH.CENTER
    footer_run = footer_para.runs[0]
    footer_run.font.size = Pt(9)
    footer_run.font.color.rgb = RGBColor(0x88, 0x88, 0x88)
    footer_run.font.italic = True

    title_p = doc.add_paragraph()
    title_p.alignment = WD_ALIGN_PARAGRAPH.CENTER
    title_run = title_p.add_run(
        "Tracking System Integration & Logistics Platform Enhancement Plan"
    )
    title_run.bold = True
    title_run.font.size = Pt(24)
    title_run.font.color.rgb = NAVY
    title_run.font.name = "Calibri"

    subtitle_p = doc.add_paragraph()
    subtitle_p.alignment = WD_ALIGN_PARAGRAPH.CENTER
    sub_run = subtitle_p.add_run("Fleet Management System")
    sub_run.font.size = Pt(16)
    sub_run.font.color.rgb = TEAL
    sub_run.font.name = "Calibri"
    subtitle_p.paragraph_format.space_after = Pt(18)

    add_horizontal_rule(doc)

    meta_table = doc.add_table(rows=4, cols=2)
    meta_table.style = "Table Grid"
    meta_table.autofit = True

    meta_data = [
        ("Project Name", "Fleet Management System"),
        ("Document Type", "Tracking Integration & Logistics Platform Enhancement Plan"),
        ("Version", "1.0"),
        ("Date", datetime.date.today().strftime("%B %d, %Y")),
    ]

    for i, (label, value) in enumerate(meta_data):
        row = meta_table.rows[i]

        label_cell = row.cells[0]
        set_cell_bg(label_cell, "1A376C")
        label_cell.vertical_alignment = WD_ALIGN_VERTICAL.CENTER
        lp = label_cell.paragraphs[0]
        lr = lp.add_run(label)
        lr.bold = True
        lr.font.color.rgb = WHITE
        lr.font.size = Pt(10.5)
        lr.font.name = "Calibri"
        lp.paragraph_format.left_indent = Pt(6)

        value_cell = row.cells[1]
        set_cell_bg(value_cell, "E8EEF7")
        value_cell.vertical_alignment = WD_ALIGN_VERTICAL.CENTER
        vp = value_cell.paragraphs[0]
        vr = vp.add_run(value)
        vr.font.color.rgb = DARK_GREY
        vr.font.size = Pt(10.5)
        vr.font.name = "Calibri"
        vp.paragraph_format.left_indent = Pt(6)

    doc.add_paragraph()

    add_section_heading(doc, "1.", "Executive Summary")
    add_body(
        doc,
        "Following the completion of the Production MVP, the next development phase "
        "focuses on integrating a comprehensive shipment tracking capability into the "
        "logistics platform. This enhancement will provide real-time visibility into "
        "shipment movement, improve operational oversight, strengthen customer "
        "confidence, and establish the foundation for future logistics intelligence "
        "features.",
    )
    add_body(
        doc,
        "The tracking integration will be implemented in phases to ensure stability, "
        "scalability, and measurable business value.",
    )

    add_section_heading(doc, "2.", "Phase 1: Tracking Infrastructure Integration")
    add_sub_heading(doc, "Objective")
    add_body(
        doc,
        "Establish the technical foundation required to connect vehicles, shipments, and "
        "location data.",
    )
    add_sub_heading(doc, "Scope")
    add_sub_heading(doc, "Vehicle Management")
    add_bullet(doc, "Register vehicles within the platform", "")
    add_bullet(doc, "Associate vehicles with carriers", "")
    add_bullet(doc, "Maintain vehicle profiles and identification information", "")
    add_sub_heading(doc, "Shipment-Vehicle Assignment")
    add_bullet(doc, "Link assigned shipments to vehicles", "")
    add_bullet(doc, "Track active shipment-vehicle relationships", "")
    add_bullet(doc, "Maintain assignment history", "")
    add_sub_heading(doc, "Tracking Data Integration")
    add_bullet(doc, "Receive and process location updates", "")
    add_bullet(doc, "Store current vehicle position information", "")
    add_bullet(doc, "Manage vehicle status information", "")
    add_sub_heading(doc, "Tracking Administration")
    add_bullet(doc, "Vehicle registration management", "")
    add_bullet(doc, "Device assignment management", "")
    add_bullet(doc, "Tracking configuration controls", "")
    add_sub_heading(doc, "Deliverables")
    add_bullet(doc, "Vehicle Management Module", "")
    add_bullet(doc, "Shipment-to-Vehicle Assignment Capability", "")
    add_bullet(doc, "Tracking Integration Layer", "")
    add_bullet(doc, "Tracking Administration Interface", "")
    add_sub_heading(doc, "Business Benefits")
    add_bullet(doc, "Foundation for all future tracking capabilities", "")
    add_bullet(doc, "Improved operational visibility", "")
    add_bullet(doc, "Accurate shipment monitoring", "")

    add_section_heading(doc, "3.", "Phase 2: Real-Time Shipment Tracking")
    add_sub_heading(doc, "Objective")
    add_body(
        doc,
        "Provide live shipment visibility to shippers, carriers, and administrators.",
    )
    add_sub_heading(doc, "Scope")
    add_sub_heading(doc, "Live Vehicle Location")
    add_bullet(doc, "Current vehicle location display", "")
    add_bullet(doc, "Vehicle movement monitoring", "")
    add_bullet(doc, "Last update timestamp visibility", "")
    add_sub_heading(doc, "Shipment Tracking Dashboard")
    add_bullet(doc, "Real-time shipment map", "")
    add_bullet(doc, "Active shipment monitoring", "")
    add_bullet(doc, "Shipment progress visualization", "")
    add_sub_heading(doc, "Shipment Tracking Page")
    add_body(doc, "Display:")
    add_bullet(doc, "Shipment information", "")
    add_bullet(doc, "Assigned carrier", "")
    add_bullet(doc, "Assigned vehicle", "")
    add_bullet(doc, "Current location", "")
    add_bullet(doc, "Current shipment status", "")
    add_bullet(doc, "Last reported activity", "")
    add_sub_heading(doc, "Tracking History")
    add_bullet(doc, "Historical location records", "")
    add_bullet(doc, "Movement timeline", "")
    add_bullet(doc, "Position history retrieval", "")
    add_sub_heading(doc, "Deliverables")
    add_bullet(doc, "Live Tracking Dashboard", "")
    add_bullet(doc, "Shipment Tracking Interface", "")
    add_bullet(doc, "Historical Tracking Records", "")
    add_sub_heading(doc, "Business Benefits")
    add_bullet(doc, "Increased customer confidence", "")
    add_bullet(doc, "Reduced status inquiry calls", "")
    add_bullet(doc, "Improved shipment visibility", "")
    add_bullet(doc, "Enhanced operational control", "")

    add_section_heading(doc, "4.", "Phase 3: Route History & Playback")
    add_sub_heading(doc, "Objective")
    add_body(
        doc,
        "Allow users and administrators to review completed and active shipment "
        "journeys.",
    )
    add_sub_heading(doc, "Scope")
    add_sub_heading(doc, "Route Playback")
    add_bullet(doc, "Historical route visualization", "")
    add_bullet(doc, "Journey replay functionality", "")
    add_bullet(doc, "Time-based movement review", "")
    add_sub_heading(doc, "Trip Analysis")
    add_bullet(doc, "Route traveled", "")
    add_bullet(doc, "Distance covered", "")
    add_bullet(doc, "Stop duration analysis", "")
    add_bullet(doc, "Travel activity history", "")
    add_sub_heading(doc, "Operational Investigation")
    add_bullet(doc, "Shipment verification", "")
    add_bullet(doc, "Route compliance monitoring", "")
    add_bullet(doc, "Delivery confirmation support", "")
    add_sub_heading(doc, "Deliverables")
    add_bullet(doc, "Route Playback Interface", "")
    add_bullet(doc, "Journey History Reports", "")
    add_bullet(doc, "Route Analysis Tools", "")
    add_sub_heading(doc, "Business Benefits")
    add_bullet(doc, "Shipment audit capability", "")
    add_bullet(doc, "Improved dispute resolution", "")
    add_bullet(doc, "Operational performance insights", "")
    add_bullet(doc, "Historical shipment verification", "")

    add_section_heading(doc, "5.", "Phase 4: Geofence-Based Logistics Automation")
    add_sub_heading(doc, "Objective")
    add_body(
        doc,
        "Automate shipment monitoring using location-based events.",
    )
    add_sub_heading(doc, "Scope")
    add_sub_heading(doc, "Pickup Geofences")
    add_bullet(doc, "Pickup area definition", "")
    add_bullet(doc, "Arrival detection", "")
    add_bullet(doc, "Departure detection", "")
    add_sub_heading(doc, "Destination Geofences")
    add_bullet(doc, "Delivery area definition", "")
    add_bullet(doc, "Destination arrival detection", "")
    add_bullet(doc, "Delivery verification support", "")
    add_sub_heading(doc, "Event Monitoring")
    add_bullet(doc, "Entry events", "")
    add_bullet(doc, "Exit events", "")
    add_bullet(doc, "Arrival notifications", "")
    add_sub_heading(doc, "Shipment Event Recording")
    add_bullet(doc, "Geofence event history", "")
    add_bullet(doc, "Automated operational logs", "")
    add_bullet(doc, "Location-based audit trail", "")
    add_sub_heading(doc, "Deliverables")
    add_bullet(doc, "Geofence Management System", "")
    add_bullet(doc, "Geofence Event Processing", "")
    add_bullet(doc, "Automated Shipment Event Recording", "")
    add_sub_heading(doc, "Business Benefits")
    add_bullet(doc, "Reduced manual monitoring", "")
    add_bullet(doc, "Increased operational accuracy", "")
    add_bullet(doc, "Improved delivery verification", "")
    add_bullet(doc, "Better shipment accountability", "")

    add_section_heading(doc, "6.", "Phase 5: Intelligent Shipment Status Automation")
    add_sub_heading(doc, "Objective")
    add_body(
        doc,
        "Reduce manual shipment updates and improve status accuracy.",
    )
    add_sub_heading(doc, "Scope")
    add_sub_heading(doc, "Automated Status Progression")
    add_body(doc, "Examples:")
    add_bullet(doc, "Pickup location reached → Shipment Ready for Pickup", "")
    add_bullet(doc, "Pickup completed → In Transit", "")
    add_bullet(doc, "Destination reached → Delivered", "")
    add_sub_heading(doc, "Event-Driven Workflow Management")
    add_bullet(doc, "Automatic shipment progression", "")
    add_bullet(doc, "Reduced user intervention", "")
    add_bullet(doc, "Location-based workflow triggers", "")
    add_sub_heading(doc, "Exception Handling")
    add_bullet(doc, "Missed route alerts", "")
    add_bullet(doc, "Delayed movement detection", "")
    add_bullet(doc, "Unexpected stop detection", "")
    add_sub_heading(doc, "Deliverables")
    add_bullet(doc, "Automated Status Engine", "")
    add_bullet(doc, "Event Processing Framework", "")
    add_bullet(doc, "Shipment Automation Rules", "")
    add_sub_heading(doc, "Business Benefits")
    add_bullet(doc, "Reduced operational workload", "")
    add_bullet(doc, "Improved shipment data accuracy", "")
    add_bullet(doc, "Faster status updates", "")
    add_bullet(doc, "Improved customer experience", "")

    add_section_heading(doc, "7.", "Phase 6: Notifications & Tracking Alerts")
    add_sub_heading(doc, "Objective")
    add_body(
        doc,
        "Keep all stakeholders informed about shipment progress.",
    )
    add_sub_heading(doc, "Scope")
    add_sub_heading(doc, "Shipment Notifications")
    add_bullet(doc, "Carrier assigned", "")
    add_bullet(doc, "Pickup completed", "")
    add_bullet(doc, "Shipment in transit", "")
    add_bullet(doc, "Shipment delivered", "")
    add_sub_heading(doc, "Location-Based Alerts")
    add_bullet(doc, "Vehicle arrived at pickup location", "")
    add_bullet(doc, "Vehicle departed pickup location", "")
    add_bullet(doc, "Vehicle arrived at destination", "")
    add_sub_heading(doc, "Administrative Alerts")
    add_bullet(doc, "Tracking interruption detection", "")
    add_bullet(doc, "Delayed shipment notifications", "")
    add_bullet(doc, "Route deviation notifications", "")
    add_sub_heading(doc, "Deliverables")
    add_bullet(doc, "Notification Service", "")
    add_bullet(doc, "Alert Management System", "")
    add_bullet(doc, "User Notification Center", "")
    add_sub_heading(doc, "Business Benefits")
    add_bullet(doc, "Faster communication", "")
    add_bullet(doc, "Improved shipment awareness", "")
    add_bullet(doc, "Reduced operational delays", "")

    add_section_heading(doc, "8.", "Phase 7: Carrier Performance & Analytics")
    add_sub_heading(doc, "Objective")
    add_body(
        doc,
        "Leverage tracking data to measure operational performance.",
    )
    add_sub_heading(doc, "Scope")
    add_sub_heading(doc, "Carrier Performance Metrics")
    add_bullet(doc, "Delivery completion rates", "")
    add_bullet(doc, "Average delivery times", "")
    add_bullet(doc, "On-time delivery performance", "")
    add_sub_heading(doc, "Vehicle Utilization Metrics")
    add_bullet(doc, "Active operating hours", "")
    add_bullet(doc, "Distance traveled", "")
    add_bullet(doc, "Asset utilization reports", "")
    add_sub_heading(doc, "Operational Dashboards")
    add_bullet(doc, "Fleet activity overview", "")
    add_bullet(doc, "Shipment activity overview", "")
    add_bullet(doc, "Delivery performance reporting", "")
    add_sub_heading(doc, "Carrier Rating & Review Integration")
    add_bullet(doc, "Post-delivery ratings", "")
    add_bullet(doc, "Customer feedback", "")
    add_bullet(doc, "Reputation scoring", "")
    add_sub_heading(doc, "Deliverables")
    add_bullet(doc, "Performance Dashboard", "")
    add_bullet(doc, "Carrier Analytics", "")
    add_bullet(doc, "Rating & Review System", "")
    add_bullet(doc, "Operational Reporting", "")
    add_sub_heading(doc, "Business Benefits")
    add_bullet(doc, "Data-driven decision making", "")
    add_bullet(doc, "Improved carrier accountability", "")
    add_bullet(doc, "Better service quality monitoring", "")
    add_bullet(doc, "Enhanced marketplace trust", "")

    add_section_heading(doc, "9.", "Recommended Implementation Timeline")
    add_styled_table(
        doc,
        headers=["Phase", "Deliverable", "Priority"],
        rows=[
            ("1", "Tracking Infrastructure Integration", "Critical"),
            ("2", "Real-Time Shipment Tracking", "Critical"),
            ("3", "Route Playback", "High"),
            ("4", "Geofence Automation", "High"),
            ("5", "Shipment Status Automation", "High"),
            ("6", "Notifications & Alerts", "Medium"),
            ("7", "Analytics, Ratings & Performance", "Medium"),
        ],
    )

    add_section_heading(doc, "10.", "Strategic Outcome")
    add_body(
        doc,
        "Upon completion of this roadmap, the platform will evolve from a shipment "
        "management system into a fully trackable logistics ecosystem capable of "
        "providing:",
    )
    add_bullet(doc, "Real-time shipment visibility", "")
    add_bullet(doc, "Route history and playback", "")
    add_bullet(doc, "Automated location-based workflows", "")
    add_bullet(doc, "Delivery verification", "")
    add_bullet(doc, "Intelligent notifications", "")
    add_bullet(doc, "Carrier accountability", "")
    add_bullet(doc, "Operational analytics", "")
    add_bullet(doc, "Enhanced customer confidence", "")
    add_body(
        doc,
        "This approach prioritizes shipment tracking and operational visibility while "
        "creating a scalable foundation for future marketplace and logistics "
        "intelligence capabilities.",
    )

    return doc


if __name__ == "__main__":
    document = build_document()
    document.save("Tracking_Integration_Roadmap.docx")
