"""
Generate a well-designed GPS Tracking Software Proposal (.docx).

Requirements
------------
    pip install python-docx

Usage
-----
    python generate_gps_proposal_docx.py

Output
------
    GPS_Tracking_Proposal.docx  (created in the current working directory)
"""

from docx import Document
from docx.shared import Pt, RGBColor, Cm
from docx.enum.text import WD_ALIGN_PARAGRAPH
from docx.enum.table import WD_ALIGN_VERTICAL
from docx.oxml.ns import qn
from docx.oxml import OxmlElement
import datetime


# ---------------------------------------------------------------------------
# Colour palette
# ---------------------------------------------------------------------------
NAVY       = RGBColor(0x1A, 0x37, 0x6C)   # primary heading colour
TEAL       = RGBColor(0x00, 0x7B, 0x83)   # secondary / accent
WHITE      = RGBColor(0xFF, 0xFF, 0xFF)
LIGHT_GREY = RGBColor(0xF2, 0xF2, 0xF2)
DARK_GREY  = RGBColor(0x40, 0x40, 0x40)


# ---------------------------------------------------------------------------
# Helper utilities
# ---------------------------------------------------------------------------

def set_cell_bg(cell, hex_color: str) -> None:
    """Fill a table cell background with a solid colour (hex, e.g. '1A376C')."""
    tc   = cell._tc
    tcPr = tc.get_or_add_tcPr()
    shd  = OxmlElement("w:shd")
    shd.set(qn("w:val"),   "clear")
    shd.set(qn("w:color"), "auto")
    shd.set(qn("w:fill"),  hex_color)
    tcPr.append(shd)


def add_horizontal_rule(doc: Document) -> None:
    """Insert a thin teal horizontal rule paragraph."""
    p      = doc.add_paragraph()
    pPr    = p._p.get_or_add_pPr()
    pBdr   = OxmlElement("w:pBdr")
    bottom = OxmlElement("w:bottom")
    bottom.set(qn("w:val"),   "single")
    bottom.set(qn("w:sz"),    "6")
    bottom.set(qn("w:space"), "1")
    bottom.set(qn("w:color"), "007B83")
    pBdr.append(bottom)
    pPr.append(pBdr)
    p.paragraph_format.space_after = Pt(0)


def add_section_heading(doc: Document, number: str, title: str) -> None:
    """Add a numbered section heading (Heading 1 style, navy background strip)."""
    p   = doc.add_paragraph(style="Heading 1")
    run = p.add_run(f"  {number}  {title}")
    run.font.color.rgb = WHITE
    run.font.bold      = True
    run.font.size      = Pt(13)
    p.paragraph_format.space_before = Pt(14)
    p.paragraph_format.space_after  = Pt(6)
    pPr = p._p.get_or_add_pPr()
    shd = OxmlElement("w:shd")
    shd.set(qn("w:val"),   "clear")
    shd.set(qn("w:color"), "auto")
    shd.set(qn("w:fill"),  "1A376C")
    pPr.append(shd)


def add_sub_heading(doc: Document, title: str) -> None:
    """Add a sub-section heading (Heading 2 style, teal text)."""
    p   = doc.add_paragraph(style="Heading 2")
    run = p.add_run(title)
    run.font.color.rgb = TEAL
    run.font.bold      = True
    run.font.size      = Pt(11)
    p.paragraph_format.space_before = Pt(10)
    p.paragraph_format.space_after  = Pt(4)


def add_body(doc: Document, text: str) -> None:
    """Add a normal body paragraph."""
    p = doc.add_paragraph(text, style="Normal")
    p.paragraph_format.space_after = Pt(6)
    for run in p.runs:
        run.font.color.rgb = DARK_GREY
        run.font.size      = Pt(10.5)


def add_bullet(doc: Document, bold_label: str, body_text: str) -> None:
    """Add a bullet point with an optional bold label followed by body text."""
    p         = doc.add_paragraph(style="List Bullet")
    label_run = p.add_run(bold_label)
    label_run.bold           = True
    label_run.font.color.rgb = NAVY
    label_run.font.size      = Pt(10.5)
    if body_text:
        body_run               = p.add_run(" " + body_text)
        body_run.font.color.rgb = DARK_GREY
        body_run.font.size      = Pt(10.5)
    p.paragraph_format.space_after = Pt(4)


def add_highlight_box(doc: Document, title: str, items: list[str]) -> None:
    """Add a small shaded highlight box with a title and bullet list."""
    table = doc.add_table(rows=1, cols=1)
    table.style = "Table Grid"
    cell = table.rows[0].cells[0]
    set_cell_bg(cell, "E8EEF7")
    para = cell.paragraphs[0]
    run = para.add_run(title)
    run.bold = True
    run.font.color.rgb = NAVY
    run.font.size = Pt(11)
    for item in items:
        p = cell.add_paragraph(style="List Bullet")
        r = p.add_run(item)
        r.font.color.rgb = DARK_GREY
        r.font.size = Pt(10.5)
    doc.add_paragraph()


# ---------------------------------------------------------------------------
# Document builder
# ---------------------------------------------------------------------------

def build_document() -> Document:
    doc = Document()

    # ── Page margins ─────────────────────────────────────────────────────────
    for section in doc.sections:
        section.top_margin    = Cm(2.0)
        section.bottom_margin = Cm(2.0)
        section.left_margin   = Cm(2.54)
        section.right_margin  = Cm(2.54)

    # ── Default Normal style ─────────────────────────────────────────────────
    normal = doc.styles["Normal"]
    normal.font.name      = "Calibri"
    normal.font.size      = Pt(10.5)
    normal.font.color.rgb = DARK_GREY

    # ── Header ───────────────────────────────────────────────────────────────
    header      = doc.sections[0].header
    header_para = header.paragraphs[0]
    header_para.text      = "GPS Tracking Solution  |  Proposal"
    header_para.alignment = WD_ALIGN_PARAGRAPH.RIGHT
    header_run            = header_para.runs[0]
    header_run.font.size      = Pt(9)
    header_run.font.color.rgb = TEAL
    header_run.font.italic    = True

    # ── Footer ───────────────────────────────────────────────────────────────
    footer      = doc.sections[0].footer
    footer_para = footer.paragraphs[0]
    footer_para.text      = f"Confidential  ·  Generated {datetime.date.today().strftime('%B %d, %Y')}"
    footer_para.alignment = WD_ALIGN_PARAGRAPH.CENTER
    footer_run            = footer_para.runs[0]
    footer_run.font.size      = Pt(9)
    footer_run.font.color.rgb = RGBColor(0x88, 0x88, 0x88)
    footer_run.font.italic    = True

    # ════════════════════════════════════════════════════════════════════════
    # COVER / TITLE BLOCK
    # ════════════════════════════════════════════════════════════════════════
    title_p = doc.add_paragraph()
    title_p.alignment = WD_ALIGN_PARAGRAPH.CENTER
    title_run = title_p.add_run("GPS Tracking Software Proposal")
    title_run.bold           = True
    title_run.font.size      = Pt(26)
    title_run.font.color.rgb = NAVY
    title_run.font.name      = "Calibri"

    subtitle_p = doc.add_paragraph()
    subtitle_p.alignment = WD_ALIGN_PARAGRAPH.CENTER
    sub_run = subtitle_p.add_run("Fleet Visibility • Security • Operational Efficiency")
    sub_run.font.size      = Pt(14)
    sub_run.font.color.rgb = TEAL
    sub_run.font.name      = "Calibri"
    subtitle_p.paragraph_format.space_after = Pt(18)

    add_horizontal_rule(doc)

    meta_table = doc.add_table(rows=4, cols=2)
    meta_table.style = "Table Grid"
    meta_table.autofit = True
    meta_data = [
        ("Prepared For", "Client Organization"),
        ("Prepared By", "Fleet Management Solutions Team"),
        ("Document Type", "GPS Tracking Software Proposal"),
        ("Date", datetime.date.today().strftime("%B %d, %Y")),
    ]

    for i, (label, value) in enumerate(meta_data):
        row = meta_table.rows[i]
        label_cell = row.cells[0]
        set_cell_bg(label_cell, "1A376C")
        label_cell.vertical_alignment = WD_ALIGN_VERTICAL.CENTER
        lp  = label_cell.paragraphs[0]
        lr  = lp.add_run(label)
        lr.bold           = True
        lr.font.color.rgb = WHITE
        lr.font.size      = Pt(10.5)
        lr.font.name      = "Calibri"
        lp.paragraph_format.left_indent = Pt(6)

        value_cell = row.cells[1]
        set_cell_bg(value_cell, "E8EEF7")
        value_cell.vertical_alignment = WD_ALIGN_VERTICAL.CENTER
        vp  = value_cell.paragraphs[0]
        vr  = vp.add_run(value)
        vr.font.color.rgb = DARK_GREY
        vr.font.size      = Pt(10.5)
        vr.font.name      = "Calibri"
        vp.paragraph_format.left_indent = Pt(6)

    doc.add_paragraph()

    # ════════════════════════════════════════════════════════════════════════
    # SECTION 1 – Executive Summary
    # ════════════════════════════════════════════════════════════════════════
    add_section_heading(doc, "1.", "Executive Summary")
    add_body(
        doc,
        "This proposal outlines a modern, enterprise-grade GPS tracking solution designed "
        "to deliver real-time fleet visibility, operational control, and actionable analytics. "
        "The platform combines a resilient backend server, a powerful web dashboard, a "
        "dedicated manager mobile app, and a lightweight mobile tracker application that "
        "allows any smartphone to function as a GPS device."
    )
    add_body(
        doc,
        "The solution is fully brandable to match your corporate identity, enabling a "
        "polished experience for administrators, fleet managers, and drivers while "
        "maintaining strict security and data governance standards."
    )

    add_highlight_box(
        doc,
        "Business Outcomes",
        [
            "Reduce fuel waste and unauthorized usage through precise trip visibility.",
            "Increase driver accountability with event-based alerts and audit trails.",
            "Improve customer service with accurate ETAs and route verification.",
            "Scale effortlessly from tens to thousands of vehicles and assets.",
        ],
    )

    # ════════════════════════════════════════════════════════════════════════
    # SECTION 2 – Solution Overview
    # ════════════════════════════════════════════════════════════════════════
    add_section_heading(doc, "2.", "Solution Overview")
    add_body(
        doc,
        "The platform is a unified ecosystem that ingests location data from dedicated GPS "
        "devices and smartphones, processes events in real time, and presents insights through "
        "web and mobile interfaces. It supports multi-tenant operations, granular access "
        "controls, and a configurable rules engine for alerts and automation."
    )

    # ════════════════════════════════════════════════════════════════════════
    # SECTION 3 – Platform Components
    # ════════════════════════════════════════════════════════════════════════
    add_section_heading(doc, "3.", "Platform Components")

    add_sub_heading(doc, "3.1 Backend Server")
    add_body(
        doc,
        "A high-performance server layer that receives, normalizes, and stores GPS data from "
        "multiple device protocols while maintaining secure, low-latency processing."
    )
    for label, body in [
        ("Real-time data ingestion:", "Supports large fleets with continuous streaming updates."),
        ("Protocol compatibility:", "Connects with popular GPS devices and custom integrations."),
        ("Event engine:", "Generates alerts for overspeeding, geofence breaches, and tamper events."),
        ("Command queue:", "Enables remote commands such as immobilization and configuration changes."),
        ("Scalable storage:", "Retains historical data for auditing, analytics, and compliance."),
    ]:
        add_bullet(doc, label, body)

    add_sub_heading(doc, "3.2 Web Management Portal")
    add_body(
        doc,
        "A responsive web console for administrators and dispatch teams to monitor fleets, "
        "configure policies, and generate reports from any modern browser."
    )
    for label, body in [
        ("Live map dashboard:", "See real-time locations, status, and health of every asset."),
        ("Device & user management:", "Role-based access, grouping, and permissions."),
        ("Geofencing tools:", "Create polygon, radius, and corridor zones with instant alerts."),
        ("Playback & trip history:", "Replay routes, stops, and idle times with timeline controls."),
        ("Reporting suite:", "Export trips, mileage, fuel, events, and utilization to PDF/CSV."),
    ]:
        add_bullet(doc, label, body)

    add_sub_heading(doc, "3.3 Manager Mobile App")
    add_body(
        doc,
        "A dedicated mobile app for supervisors to monitor operations, respond to alerts, "
        "and stay informed while away from the office."
    )
    for label, body in [
        ("Real-time fleet view:", "Access live locations, status filters, and quick summaries."),
        ("Alert center:", "Receive push notifications for critical events and acknowledge them."),
        ("On-the-go reports:", "View daily trips, stop reports, and driver performance."),
        ("Geofence monitoring:", "Review zone activity and manage quick perimeter updates."),
        ("Secure access:", "Biometric and session protections for managers in the field."),
    ]:
        add_bullet(doc, label, body)

    add_sub_heading(doc, "3.4 Mobile Tracker App (Phone-as-Tracker)")
    add_body(
        doc,
        "A lightweight tracker application that turns any smartphone into a GPS device, "
        "ideal for contractors, temporary assets, or vehicles without dedicated hardware."
    )
    for label, body in [
        ("Background tracking:", "Runs continuously with intelligent battery optimization."),
        ("Offline buffering:", "Stores location points and syncs automatically when online."),
        ("Driver status:", "Quick status toggles for available, on-trip, or idle."),
        ("SOS & panic events:", "Instant emergency alerts sent to the control center."),
        ("Trip tagging:", "Annotate deliveries or assignments for better reporting."),
    ]:
        add_bullet(doc, label, body)

    # ════════════════════════════════════════════════════════════════════════
    # SECTION 4 – Core Capabilities
    # ════════════════════════════════════════════════════════════════════════
    add_section_heading(doc, "4.", "Core Capabilities")
    add_body(
        doc,
        "The platform includes a robust set of capabilities that ensure full visibility "
        "and control over fleet operations."
    )
    for label, body in [
        ("Real-time tracking:", "Sub-minute updates with map clustering and status icons."),
        ("Geofencing & alerts:", "Entry, exit, dwell-time, and route deviation notifications."),
        ("Driver behavior monitoring:", "Overspeeding, harsh braking, and idling events."),
        ("Maintenance reminders:", "Service schedules based on time, mileage, or engine hours."),
        ("Multi-asset support:", "Vehicles, generators, containers, and movable equipment."),
        ("Integrations:", "APIs for ERP, dispatch, and business intelligence platforms."),
    ]:
        add_bullet(doc, label, body)

    # ════════════════════════════════════════════════════════════════════════
    # SECTION 5 – Reporting & Analytics
    # ════════════════════════════════════════════════════════════════════════
    add_section_heading(doc, "5.", "Reporting & Analytics")
    add_body(
        doc,
        "A comprehensive reporting suite delivers insight into performance, cost, and "
        "compliance, enabling data-driven decisions."
    )
    for label, body in [
        ("Trip & stop reports:", "Detailed routes, stops, idle time, and distance metrics."),
        ("Fuel & utilization:", "Track consumption patterns and asset utilization rates."),
        ("Event audit trails:", "Downloadable records for investigations and compliance."),
        ("Custom dashboards:", "Configurable widgets for KPIs and alerts."),
    ]:
        add_bullet(doc, label, body)

    # ════════════════════════════════════════════════════════════════════════
    # SECTION 6 – Security & Reliability
    # ════════════════════════════════════════════════════════════════════════
    add_section_heading(doc, "6.", "Security & Reliability")
    add_body(
        doc,
        "Security, uptime, and data integrity are built into every layer of the platform."
    )
    for label, body in [
        ("Encrypted communications:", "TLS-secured device connections and web access."),
        ("Role-based access:", "Granular permissions for admins, managers, and operators."),
        ("Audit logging:", "Full history of user actions and system events."),
        ("High availability options:", "Redundancy and backups to protect business continuity."),
        ("Data residency controls:", "Deploy on-premise or in approved cloud regions."),
    ]:
        add_bullet(doc, label, body)

    # ════════════════════════════════════════════════════════════════════════
    # SECTION 7 – Implementation & Support
    # ════════════════════════════════════════════════════════════════════════
    add_section_heading(doc, "7.", "Implementation & Support")
    add_body(
        doc,
        "We provide a structured deployment plan and ongoing support to ensure rapid "
        "adoption and long-term success."
    )
    for label, body in [
        ("Discovery & requirements:", "Confirm fleet structure, device types, and reporting needs."),
        ("System setup:", "Configure server, security policies, and integrations."),
        ("Onboarding & training:", "Guided sessions for administrators and managers."),
        ("Go-live support:", "Monitoring during initial operations with fast issue resolution."),
        ("Managed services:", "Optional monitoring, backups, and feature enhancements."),
    ]:
        add_bullet(doc, label, body)

    # ════════════════════════════════════════════════════════════════════════
    # SECTION 8 – Branding & Ownership
    # ════════════════════════════════════════════════════════════════════════
    add_section_heading(doc, "8.", "Branding & Ownership")
    add_body(
        doc,
        "The platform is delivered as a fully brandable solution. Logos, color schemes, "
        "and portal identity can be aligned with your corporate brand, ensuring a seamless "
        "experience for internal teams and external stakeholders."
    )
    for label, body in [
        ("Custom branding:", "Your logo, colors, and naming across web and mobile apps."),
        ("White-label experience:", "Branded login screens, email templates, and reports."),
        ("Ownership-ready deliverables:", "Admin access, deployment documentation, and data control."),
    ]:
        add_bullet(doc, label, body)

    # ════════════════════════════════════════════════════════════════════════
    # SECTION 9 – Why This Solution
    # ════════════════════════════════════════════════════════════════════════
    add_section_heading(doc, "9.", "Why This Solution")
    add_body(
        doc,
        "This GPS tracking platform is built for performance, scalability, and operational "
        "clarity. It delivers immediate visibility while providing the flexibility to grow, "
        "integrate, and adapt to evolving fleet requirements."
    )
    add_body(
        doc,
        "We welcome the opportunity to tailor the deployment to your fleet size, device "
        "inventory, and reporting priorities. A pilot environment can be launched quickly "
        "to validate outcomes before full rollout."
    )

    doc.add_paragraph()
    add_horizontal_rule(doc)
    closing = doc.add_paragraph()
    closing.alignment = WD_ALIGN_PARAGRAPH.CENTER
    cr = closing.add_run("End of Proposal")
    cr.italic           = True
    cr.font.size        = Pt(9)
    cr.font.color.rgb   = RGBColor(0x88, 0x88, 0x88)

    return doc


# ---------------------------------------------------------------------------
# Entry point
# ---------------------------------------------------------------------------

if __name__ == "__main__":
    output_path = "GPS_Tracking_Proposal.docx"
    doc = build_document()
    doc.save(output_path)
    print(f"✅  Document saved: {output_path}")
