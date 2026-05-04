"""
Generate a well-designed Service Level Agreement (SLA) Document (.docx)
for the Fleet Management System project.

Requirements
------------
    pip install python-docx

Usage
-----
    python generate_sla_docx.py

Output
------
    Service_Level_Agreement.docx  (created in the current working directory)
"""

from docx import Document
from docx.shared import Pt, RGBColor, Cm
from docx.enum.text import WD_ALIGN_PARAGRAPH
from docx.enum.table import WD_ALIGN_VERTICAL
from docx.oxml.ns import qn
from docx.oxml import OxmlElement
import datetime


# ---------------------------------------------------------------------------
# Color palette  (identical to generate_sow_docx.py and generate_pricing_docx.py)
# ---------------------------------------------------------------------------
NAVY       = RGBColor(0x1A, 0x37, 0x6C)   # primary heading colour
TEAL       = RGBColor(0x00, 0x7B, 0x83)   # secondary / accent
WHITE      = RGBColor(0xFF, 0xFF, 0xFF)
LIGHT_GREY = RGBColor(0xF2, 0xF2, 0xF2)
DARK_GREY  = RGBColor(0x40, 0x40, 0x40)


# ---------------------------------------------------------------------------
# Helper utilities  (identical styling to generate_sow_docx.py)
# ---------------------------------------------------------------------------

def set_cell_bg(cell, hex_color: str) -> None:
    """Fill a table cell background with a solid color (hex, e.g. '1A376C')."""
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


def add_note_heading(doc: Document, title: str) -> None:
    """Add a callout / note heading (bold-italic teal, used for sub-sections)."""
    p   = doc.add_paragraph(style="Normal")
    run = p.add_run(title)
    run.bold           = True
    run.italic         = True
    run.font.color.rgb = TEAL
    run.font.size      = Pt(11)
    p.paragraph_format.space_before = Pt(8)
    p.paragraph_format.space_after  = Pt(2)


# ---------------------------------------------------------------------------
# SLA table helper
# ---------------------------------------------------------------------------

def add_sla_table(doc: Document, headers: list, rows: list) -> None:
    """
    Render a styled SLA data table.

    headers – list of column header strings
    rows    – list of row tuples  (each tuple has len == len(headers))
    """
    num_cols = len(headers)
    table    = doc.add_table(rows=1 + len(rows), cols=num_cols)
    table.style = "Table Grid"

    # Header row
    hdr_row = table.rows[0]
    for col_idx, hdr_text in enumerate(headers):
        cell = hdr_row.cells[col_idx]
        set_cell_bg(cell, "1A376C")
        cell.vertical_alignment = WD_ALIGN_VERTICAL.CENTER
        para = cell.paragraphs[0]
        run  = para.add_run(hdr_text)
        run.bold           = True
        run.font.color.rgb = WHITE
        run.font.size      = Pt(10.5)
        run.font.name      = "Calibri"
        para.paragraph_format.left_indent = Pt(4)

    # Data rows — alternate background shading
    for row_idx, row_data in enumerate(rows):
        bg    = "E8EEF7" if row_idx % 2 == 0 else "F2F2F2"
        t_row = table.rows[1 + row_idx]
        for col_idx, cell_text in enumerate(row_data):
            cell = t_row.cells[col_idx]
            set_cell_bg(cell, bg)
            cell.vertical_alignment = WD_ALIGN_VERTICAL.CENTER
            para = cell.paragraphs[0]
            run  = para.add_run(cell_text)
            run.font.color.rgb = DARK_GREY
            run.font.size      = Pt(10.5)
            run.font.name      = "Calibri"
            para.paragraph_format.left_indent = Pt(4)

    doc.add_paragraph()   # spacer after table


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
    header_para.text      = "Fleet Management System  |  Service Level Agreement (SLA)"
    header_para.alignment = WD_ALIGN_PARAGRAPH.RIGHT
    header_run            = header_para.runs[0]
    header_run.font.size      = Pt(9)
    header_run.font.color.rgb = TEAL
    header_run.font.italic    = True

    # ── Footer ───────────────────────────────────────────────────────────────
    footer      = doc.sections[0].footer
    footer_para = footer.paragraphs[0]
    footer_para.text = (
        f"Confidential  ·  Generated {datetime.date.today().strftime('%B %d, %Y')}"
    )
    footer_para.alignment = WD_ALIGN_PARAGRAPH.CENTER
    footer_run            = footer_para.runs[0]
    footer_run.font.size      = Pt(9)
    footer_run.font.color.rgb = RGBColor(0x88, 0x88, 0x88)
    footer_run.font.italic    = True

    # ════════════════════════════════════════════════════════════════════════
    # COVER / TITLE BLOCK
    # ════════════════════════════════════════════════════════════════════════
    title_p           = doc.add_paragraph()
    title_p.alignment = WD_ALIGN_PARAGRAPH.CENTER
    title_run         = title_p.add_run("Service Level Agreement (SLA)")
    title_run.bold           = True
    title_run.font.size      = Pt(26)
    title_run.font.color.rgb = NAVY
    title_run.font.name      = "Calibri"

    subtitle_p           = doc.add_paragraph()
    subtitle_p.alignment = WD_ALIGN_PARAGRAPH.CENTER
    sub_run              = subtitle_p.add_run("Fleet Management System")
    sub_run.font.size      = Pt(16)
    sub_run.font.color.rgb = TEAL
    sub_run.font.name      = "Calibri"
    subtitle_p.paragraph_format.space_after = Pt(18)

    add_horizontal_rule(doc)

    # ── Metadata table ───────────────────────────────────────────────────────
    meta_table         = doc.add_table(rows=4, cols=2)
    meta_table.style   = "Table Grid"
    meta_table.autofit = True

    meta_data = [
        ("Project Name",    "Fleet Management System"),
        ("Document Type",   "Service Level Agreement (SLA)"),
        ("Version",         "1.0"),
        ("Date",            datetime.date.today().strftime("%B %d, %Y")),
    ]

    for i, (label, value) in enumerate(meta_data):
        row = meta_table.rows[i]

        label_cell = row.cells[0]
        set_cell_bg(label_cell, "1A376C")
        label_cell.vertical_alignment = WD_ALIGN_VERTICAL.CENTER
        lp = label_cell.paragraphs[0]
        lr = lp.add_run(label)
        lr.bold           = True
        lr.font.color.rgb = WHITE
        lr.font.size      = Pt(10.5)
        lr.font.name      = "Calibri"
        lp.paragraph_format.left_indent = Pt(6)

        value_cell = row.cells[1]
        set_cell_bg(value_cell, "E8EEF7")
        value_cell.vertical_alignment = WD_ALIGN_VERTICAL.CENTER
        vp = value_cell.paragraphs[0]
        vr = vp.add_run(value)
        vr.font.color.rgb = DARK_GREY
        vr.font.size      = Pt(10.5)
        vr.font.name      = "Calibri"
        vp.paragraph_format.left_indent = Pt(6)

    doc.add_paragraph()   # spacer

    # ════════════════════════════════════════════════════════════════════════
    # SECTION 1 – Uptime Guarantees
    # ════════════════════════════════════════════════════════════════════════
    add_section_heading(doc, "1.", "Uptime Guarantees")

    add_body(doc,
        "The Service Provider commits to maintaining the Fleet Management System platform "
        "at or above the minimum uptime thresholds defined in this section. Uptime is "
        "calculated on a calendar-month basis, excluding pre-approved scheduled "
        "maintenance windows as defined in Section 3."
    )

    add_sub_heading(doc, "1.1  Service Availability Commitment")
    add_body(doc,
        "The platform shall maintain a minimum monthly uptime of 99.9%, equating to no "
        "more than approximately 43.8 minutes of unplanned downtime per month. This "
        "commitment applies to all core platform services, including real-time tracking, "
        "dashboard access, reporting, and alert delivery."
    )

    add_sla_table(doc,
        headers=["Service Component", "Uptime Target", "Max Allowed Monthly Downtime"],
        rows=[
            ("Core Tracking Engine",     "99.9%", "~43.8 minutes"),
            ("Web Dashboard & UI",        "99.9%", "~43.8 minutes"),
            ("Reporting & Analytics",     "99.5%", "~3.6 hours"),
            ("Alert & Notification System","99.5%", "~3.6 hours"),
            ("API Endpoints",             "99.9%", "~43.8 minutes"),
            ("Data Storage & Database",   "99.99%","~4.4 minutes"),
        ]
    )

    add_sub_heading(doc, "1.2  Uptime Measurement & Reporting")
    add_body(doc,
        "Uptime is monitored continuously using automated health-check probes at "
        "intervals of no greater than five (5) minutes. A monthly uptime report will be "
        "made available to the Client upon request or delivered automatically as part of "
        "the agreed support package. The calculation formula is:"
    )
    add_bullet(doc, "Uptime % =",
               "((Total Minutes in Month – Unplanned Downtime Minutes) / "
               "Total Minutes in Month) × 100")

    add_sub_heading(doc, "1.3  Service Credits")
    add_body(doc,
        "In the event that the monthly uptime commitment is not met, the Client shall be "
        "eligible to receive service credits against the next billing cycle as outlined "
        "below. Service credits are the Client's sole remedy for uptime failures."
    )
    add_sla_table(doc,
        headers=["Monthly Uptime Achieved", "Service Credit (% of Monthly Fee)"],
        rows=[
            ("99.0% – 99.8%",  "5%"),
            ("95.0% – 98.9%",  "10%"),
            ("90.0% – 94.9%",  "20%"),
            ("Below 90.0%",    "30%"),
        ]
    )
    add_body(doc,
        "Note: Service credits do not apply to downtime caused by Client-side "
        "infrastructure failures, third-party network outages, force majeure events, or "
        "downtime occurring during pre-approved maintenance windows."
    )

    # ════════════════════════════════════════════════════════════════════════
    # SECTION 2 – Support Response Times
    # ════════════════════════════════════════════════════════════════════════
    add_section_heading(doc, "2.", "Support Response Times")

    add_body(doc,
        "The Service Provider shall respond to and resolve support incidents in "
        "accordance with the severity classifications and response time targets defined "
        "below. All incidents must be reported through the designated support channel "
        "(email, support portal, or phone, depending on the active support tier)."
    )

    add_sub_heading(doc, "2.1  Severity Classification")
    add_body(doc,
        "Incidents are categorized into three (3) severity levels based on their impact "
        "on platform operations and business continuity:"
    )
    add_bullet(doc, "Critical (Severity 1):",
               "Complete platform outage or a core feature (live tracking, "
               "immobilization commands) is non-functional, causing severe business impact "
               "with no available workaround.")
    add_bullet(doc, "High (Severity 2):",
               "A significant feature or module is impaired (e.g., reports unavailable, "
               "geofence alerts not firing), with major impact on daily operations. A "
               "partial workaround may exist.")
    add_bullet(doc, "Normal (Severity 3):",
               "A minor feature is degraded or a non-critical bug is present, causing "
               "limited operational impact. A workaround is readily available.")

    add_sub_heading(doc, "2.2  Response & Resolution Targets")
    add_body(doc,
        "The following table defines the maximum target response and resolution times for "
        "each severity level. 'Response Time' refers to the time between incident "
        "submission and acknowledgement by a support engineer. 'Resolution Time' refers "
        "to the target time to restore normal service."
    )
    add_sla_table(doc,
        headers=["Severity", "Description", "Initial Response", "Target Resolution", "Support Hours"],
        rows=[
            ("Critical (P1)", "Full outage / core feature down",      "Within 1 hour",   "Within 4 hours",   "24 / 7 / 365"),
            ("High (P2)",     "Major feature impaired",                "Within 4 hours",  "Within 24 hours",  "Business hours + on-call"),
            ("Normal (P3)",   "Minor bug / cosmetic issue",            "Within 1 business day", "Within 5 business days", "Business hours"),
        ]
    )
    add_body(doc,
        "Business hours are defined as Monday through Friday, 08:00–18:00 (local client "
        "timezone), excluding public holidays. The support team is reachable outside "
        "business hours for Critical (P1) incidents via the emergency hotline."
    )

    add_sub_heading(doc, "2.3  Escalation Procedure")
    add_body(doc,
        "If a reported incident is not acknowledged or resolved within the target "
        "timeframes, the following escalation path will be triggered automatically:"
    )
    add_bullet(doc, "Level 1 – Support Engineer:",
               "First point of contact. Handles incident triage, initial diagnosis, and "
               "applies known fixes or workarounds.")
    add_bullet(doc, "Level 2 – Senior Engineer / Team Lead:",
               "Engaged if the incident is not resolved within 50% of the resolution "
               "window. Conducts deep-dive investigation and coordinates resources.")
    add_bullet(doc, "Level 3 – Management / Account Director:",
               "Engaged for Critical incidents exceeding the 4-hour resolution target or "
               "for any incident causing significant reputational or financial impact to "
               "the Client.")

    add_sub_heading(doc, "2.4  Support Channels")
    add_sla_table(doc,
        headers=["Support Tier", "Available Channels", "Coverage"],
        rows=[
            ("Basic",    "Email only",                           "Business hours"),
            ("Standard", "Email, Support Portal, Live Chat",     "Business hours + on-call for P1"),
            ("Premium",  "Email, Portal, Chat, Phone / Hotline", "24 / 7 / 365 for all severities"),
        ]
    )

    # ════════════════════════════════════════════════════════════════════════
    # SECTION 3 – Maintenance Windows
    # ════════════════════════════════════════════════════════════════════════
    add_section_heading(doc, "3.", "Maintenance Windows")

    add_body(doc,
        "Scheduled maintenance is necessary to apply security patches, platform updates, "
        "database optimizations, and infrastructure improvements. All planned maintenance "
        "activities are carried out within the designated maintenance windows defined "
        "below and are excluded from uptime calculations."
    )

    add_sub_heading(doc, "3.1  Standard Scheduled Maintenance")
    add_body(doc,
        "Routine maintenance is performed on a weekly cadence during low-traffic periods "
        "to minimize operational disruption. The standard maintenance window is:"
    )
    add_sla_table(doc,
        headers=["Maintenance Type", "Schedule", "Typical Duration", "Impact"],
        rows=[
            ("Routine / Weekly",    "Every Sunday, 02:00 – 04:00 (UTC)",        "Up to 2 hours",  "Possible brief service interruptions"),
            ("Monthly Patch Cycle", "First Sunday of each month, 01:00 – 05:00 (UTC)", "Up to 4 hours", "Planned service downtime"),
            ("Emergency Hotfix",    "As required (see Section 3.3)",             "Variable",       "Minimal; applied with zero-downtime strategy where possible"),
        ]
    )

    add_sub_heading(doc, "3.2  Advance Notification")
    add_body(doc,
        "The Service Provider shall notify the Client in advance of any scheduled "
        "maintenance activity in accordance with the following notice periods:"
    )
    add_bullet(doc, "Routine Weekly Maintenance:",
               "A standing notification is issued at contract commencement. No "
               "additional per-event notice is required for windows that fall within the "
               "standard weekly schedule.")
    add_bullet(doc, "Monthly Patch Cycle:",
               "Minimum seventy-two (72) hours advance notice via email to the "
               "designated Client contact(s).")
    add_bullet(doc, "Non-Standard / Extended Maintenance:",
               "Minimum seven (7) calendar days advance notice. The Client may request "
               "a reschedule up to forty-eight (48) hours before the planned window.")
    add_bullet(doc, "Emergency Hotfix:",
               "Best-effort notification as soon as the need is identified, typically "
               "no less than one (1) hour before commencement.")

    add_sub_heading(doc, "3.3  Emergency Maintenance")
    add_body(doc,
        "In cases where a critical security vulnerability, data integrity risk, or "
        "imminent service threat is identified, the Service Provider reserves the right "
        "to perform emergency maintenance outside of the standard windows. The following "
        "conditions apply:"
    )
    add_bullet(doc, "Immediate Action:",
               "Emergency patches may be applied at any time if a zero-day exploit or "
               "active attack is detected, with simultaneous notification to the Client.")
    add_bullet(doc, "Zero-Downtime Deployment:",
               "Where technically feasible, emergency fixes will be deployed using "
               "rolling updates or blue-green deployment strategies to eliminate or "
               "minimize service interruption.")
    add_bullet(doc, "Post-Maintenance Report:",
               "A written incident and maintenance report will be provided to the Client "
               "within twenty-four (24) hours of emergency maintenance completion.")

    add_sub_heading(doc, "3.4  Maintenance Communication")
    add_sla_table(doc,
        headers=["Notice Type", "Notification Period", "Delivery Method"],
        rows=[
            ("Routine Weekly Window",    "Standing notice at contract start",  "Email / System Banner"),
            ("Monthly Patch Cycle",      "≥ 72 hours in advance",              "Email to designated contact"),
            ("Non-Standard Extended",    "≥ 7 calendar days in advance",       "Email + Support Portal"),
            ("Emergency Hotfix",         "≥ 1 hour (best effort)",             "Email + Phone (Premium tier)"),
        ]
    )

    # ════════════════════════════════════════════════════════════════════════
    # SECTION 4 – General Terms
    # ════════════════════════════════════════════════════════════════════════
    add_section_heading(doc, "4.", "General Terms & Exclusions")

    add_body(doc,
        "The commitments outlined in this SLA are subject to the following general "
        "terms and exclusions. Downtime or degraded performance resulting from the "
        "following circumstances will not be counted against the uptime commitment and "
        "will not qualify for service credits:"
    )
    add_bullet(doc, "Force Majeure:",
               "Events beyond the reasonable control of either party, including natural "
               "disasters, acts of government, or widespread internet infrastructure "
               "failures.")
    add_bullet(doc, "Client-Side Issues:",
               "Failures caused by the Client's own network, hardware, or software "
               "infrastructure, including ISP outages affecting Client connectivity.")
    add_bullet(doc, "Approved Maintenance Windows:",
               "Any planned downtime occurring within the pre-approved maintenance "
               "schedules defined in Section 3.")
    add_bullet(doc, "Misuse or Unauthorized Modifications:",
               "Degradation or outages caused by Client modifications, unauthorized "
               "API usage, or actions outside the scope of the agreed service.")
    add_bullet(doc, "Third-Party Services:",
               "Failures in third-party platforms or services that are outside the "
               "Service Provider's operational control (e.g., mapping tile providers, "
               "SMS gateway outages).")

    add_body(doc,
        "This SLA is incorporated by reference into the Master Service Agreement (MSA) "
        "or Statement of Work (SoW) governing the relationship between the Service "
        "Provider and the Client. In the event of any conflict, the terms of the MSA "
        "shall take precedence. Either party may request a review and revision of this "
        "SLA with a minimum of thirty (30) days written notice."
    )

    add_horizontal_rule(doc)

    # ── Signature block ───────────────────────────────────────────────────────
    doc.add_paragraph()
    sig_table       = doc.add_table(rows=3, cols=2)
    sig_table.style = "Table Grid"

    sig_headers = ["Service Provider", "Client"]
    for col_idx, hdr in enumerate(sig_headers):
        cell = sig_table.rows[0].cells[col_idx]
        set_cell_bg(cell, "1A376C")
        cell.vertical_alignment = WD_ALIGN_VERTICAL.CENTER
        para = cell.paragraphs[0]
        run  = para.add_run(hdr)
        run.bold           = True
        run.font.color.rgb = WHITE
        run.font.size      = Pt(10.5)
        run.font.name      = "Calibri"
        para.alignment     = WD_ALIGN_PARAGRAPH.CENTER

    sig_rows = [
        ("Authorized Signature: _________________", "Authorized Signature: _________________"),
        ("Name & Title: _________________________", "Name & Title: _________________________"),
    ]
    for row_idx, (left, right) in enumerate(sig_rows):
        bg   = "E8EEF7" if row_idx % 2 == 0 else "F2F2F2"
        row  = sig_table.rows[1 + row_idx]
        for col_idx, cell_text in enumerate([left, right]):
            cell = row.cells[col_idx]
            set_cell_bg(cell, bg)
            cell.vertical_alignment = WD_ALIGN_VERTICAL.CENTER
            para = cell.paragraphs[0]
            run  = para.add_run(cell_text)
            run.font.color.rgb = DARK_GREY
            run.font.size      = Pt(10.5)
            run.font.name      = "Calibri"
            para.paragraph_format.left_indent = Pt(6)

    doc.add_paragraph()

    # ── Copyright / confidentiality notice ───────────────────────────────────
    cr_p   = doc.add_paragraph()
    cr_p.alignment = WD_ALIGN_PARAGRAPH.CENTER
    cr     = cr_p.add_run(
        f"© {datetime.date.today().year} Fleet Management System. "
        "All rights reserved. This document is confidential and intended solely "
        "for the named recipient(s)."
    )
    cr.font.size      = Pt(8.5)
    cr.font.italic    = True
    cr.font.color.rgb = RGBColor(0x88, 0x88, 0x88)

    return doc


# ---------------------------------------------------------------------------
# Entry point
# ---------------------------------------------------------------------------

if __name__ == "__main__":
    output_path = "Service_Level_Agreement.docx"
    doc = build_document()
    doc.save(output_path)
    print(f"✅  Document saved: {output_path}")
