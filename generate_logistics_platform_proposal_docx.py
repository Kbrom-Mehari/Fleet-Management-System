"""
Generate a polished Strategic Logistics Platform Proposal (.docx).

Requirements
------------
    pip install python-docx

Usage
-----
    python generate_logistics_platform_proposal_docx.py

Output
------
    Logistics_Platform_Proposal.docx  (created in the current working directory)
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
NAVY = RGBColor(0x1A, 0x37, 0x6C)      # primary heading colour
TEAL = RGBColor(0x00, 0x7B, 0x83)      # secondary / accent
WHITE = RGBColor(0xFF, 0xFF, 0xFF)
LIGHT_GREY = RGBColor(0xF2, 0xF2, 0xF2)
DARK_GREY = RGBColor(0x40, 0x40, 0x40)


# ---------------------------------------------------------------------------
# Helper utilities
# ---------------------------------------------------------------------------

def set_cell_bg(cell, hex_color: str) -> None:
    """Fill a table cell background with a solid colour (hex, e.g. '1A376C')."""
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
        section.top_margin = Cm(2.0)
        section.bottom_margin = Cm(2.0)
        section.left_margin = Cm(2.54)
        section.right_margin = Cm(2.54)

    # ── Default Normal style ─────────────────────────────────────────────────
    normal = doc.styles["Normal"]
    normal.font.name = "Calibri"
    normal.font.size = Pt(10.5)
    normal.font.color.rgb = DARK_GREY

    # ── Header ───────────────────────────────────────────────────────────────
    header = doc.sections[0].header
    header_para = header.paragraphs[0]
    header_para.text = "Strategic Logistics Platform  |  Proposal"
    header_para.alignment = WD_ALIGN_PARAGRAPH.RIGHT
    header_run = header_para.runs[0]
    header_run.font.size = Pt(9)
    header_run.font.color.rgb = TEAL
    header_run.font.italic = True

    # ── Footer ───────────────────────────────────────────────────────────────
    footer = doc.sections[0].footer
    footer_para = footer.paragraphs[0]
    footer_para.text = f"Confidential  ·  Generated {datetime.date.today().strftime('%B %d, %Y')}"
    footer_para.alignment = WD_ALIGN_PARAGRAPH.CENTER
    footer_run = footer_para.runs[0]
    footer_run.font.size = Pt(9)
    footer_run.font.color.rgb = RGBColor(0x88, 0x88, 0x88)
    footer_run.font.italic = True

    # ════════════════════════════════════════════════════════════════════════
    # COVER / TITLE BLOCK
    # ════════════════════════════════════════════════════════════════════════
    title_p = doc.add_paragraph()
    title_p.alignment = WD_ALIGN_PARAGRAPH.CENTER
    title_run = title_p.add_run("Strategic Logistics Platform Proposal")
    title_run.bold = True
    title_run.font.size = Pt(26)
    title_run.font.color.rgb = NAVY
    title_run.font.name = "Calibri"

    subtitle_p = doc.add_paragraph()
    subtitle_p.alignment = WD_ALIGN_PARAGRAPH.CENTER
    sub_run = subtitle_p.add_run("Operational Clarity • Live Visibility • Smarter Routing")
    sub_run.font.size = Pt(14)
    sub_run.font.color.rgb = TEAL
    sub_run.font.name = "Calibri"
    subtitle_p.paragraph_format.space_after = Pt(18)

    add_horizontal_rule(doc)

    meta_table = doc.add_table(rows=4, cols=2)
    meta_table.style = "Table Grid"
    meta_table.autofit = True
    meta_data = [
        ("Prepared For", "Client Organization"),
        ("Prepared By", "Fleet Management Solutions Team"),
        ("Document Type", "Strategic Logistics Platform Proposal"),
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

    # ════════════════════════════════════════════════════════════════════════
    # SECTION 1 – Executive Summary
    # ════════════════════════════════════════════════════════════════════════
    add_section_heading(doc, "1.", "Executive Summary")
    add_body(
        doc,
        "Many logistics teams still rely on phone calls, emails, and spreadsheets to plan shipments "
        "and track progress. This slows down assignment, increases coordination costs, and makes it "
        "hard to give customers reliable updates."
    )
    add_body(
        doc,
        "The proposed platform brings shipment creation, carrier selection, tracking, and performance "
        "management into one clear workflow. It reduces manual work, improves on-time delivery, and "
        "gives leadership the visibility needed to make better decisions."
    )
    add_body(
        doc,
        "By combining live tracking with route optimization, the platform improves customer experience "
        "and provides a competitive advantage through faster, more predictable service."
    )

    add_highlight_box(
        doc,
        "Expected Business Results",
        [
            "Faster shipment assignment with fewer manual follow-ups.",
            "Lower operating costs through smarter routing and reduced empty miles.",
            "Higher customer satisfaction with accurate ETAs and proactive updates.",
            "Stronger carrier performance through transparent accountability.",
        ],
    )

    # ════════════════════════════════════════════════════════════════════════
    # SECTION 2 – Solution Overview
    # ════════════════════════════════════════════════════════════════════════
    add_section_heading(doc, "2.", "Solution Overview")
    add_body(
        doc,
        "The platform provides a single place to manage shipments, carriers, status updates, and "
        "financial settlement. It is designed for operations teams and decision makers who need "
        "simple, reliable information and consistent processes across locations and partners."
    )

    # ════════════════════════════════════════════════════════════════════════
    # SECTION 3 – Core Workflow Modules
    # ════════════════════════════════════════════════════════════════════════
    add_section_heading(doc, "3.", "Core Workflow Modules")

    add_sub_heading(doc, "3.1 Smart Shipment Creation & Carrier Matching")
    add_body(
        doc,
        "Operations teams can create shipments quickly with guided forms and reusable templates. "
        "The system matches loads to available carriers based on capacity, cost, and past performance, "
        "reducing back-and-forth calls and speeding assignment."
    )
    for label, body in [
        ("Fast shipment setup:", "Standard templates reduce errors and rework."),
        ("Carrier recommendations:", "Qualified carriers are suggested instantly."),
        ("Quick posting & acceptance:", "Shorten the time from request to confirmed assignment."),
        ("Better carrier utilization:", "Use available capacity more efficiently."),
    ]:
        add_bullet(doc, label, body)

    add_sub_heading(doc, "3.2 Status & Event Tracking")
    add_body(
        doc,
        "Shipments are tracked through clear milestones and exceptions so teams stay aligned and "
        "customers receive consistent updates."
    )
    for label, body in [
        ("Milestone visibility:", "Pickup, in-transit, at-hub, and delivery confirmations."),
        ("Exception alerts:", "Delays, route deviations, and dwell-time issues flagged early."),
        ("Customer notifications:", "Automated updates reduce inbound status calls."),
        ("Proof of delivery:", "Documents and signatures stored in one place."),
    ]:
        add_bullet(doc, label, body)

    add_sub_heading(doc, "3.3 Settlement & Payment")
    add_body(
        doc,
        "Payment workflows are simplified so finance teams can invoice faster and resolve disputes with "
        "clear documentation."
    )
    for label, body in [
        ("Automated invoicing:", "Generate invoices directly from completed shipments."),
        ("Dispute tracking:", "Reduce friction with auditable shipment history."),
        ("Payment visibility:", "Know what is pending, approved, and paid."),
        ("Profitability insights:", "Track cost per shipment and margin trends."),
    ]:
        add_bullet(doc, label, body)

    # ════════════════════════════════════════════════════════════════════════
    # SECTION 4 – Real-Time Live Shipment Tracking
    # ════════════════════════════════════════════════════════════════════════
    add_section_heading(doc, "4.", "Real-Time Live Shipment Tracking")
    add_body(
        doc,
        "Live tracking gives operations teams continuous visibility into every shipment. The platform "
        "supports both GPS tracking devices and smartphone-based driver tracking, allowing you to "
        "choose the right approach by fleet and cost model."
    )
    add_body(
        doc,
        "Smartphone tracking reduces hardware costs and enables faster onboarding for contractors or "
        "seasonal fleets without sacrificing real-time visibility."
    )
    for label, body in [
        ("Device and smartphone tracking:", "Use existing GPS units or driver phones."),
        ("Lower hardware costs:", "Reduce spend by relying on smartphone tracking where suitable."),
        ("Faster adoption:", "Bring new drivers online quickly without device rollout delays."),
        ("Proactive exception response:", "Act early when shipments fall behind schedule."),
        ("Customer-facing visibility:", "Shareable tracking links build trust and reduce calls."),
    ]:
        add_bullet(doc, label, body)

    # ════════════════════════════════════════════════════════════════════════
    # SECTION 5 – Route Optimization & ETA Intelligence
    # ════════════════════════════════════════════════════════════════════════
    add_section_heading(doc, "5.", "Route Optimization & ETA Intelligence")
    add_body(
        doc,
        "Route optimization reduces cost and improves delivery reliability by recommending the best "
        "path and stop sequence for each shipment. ETAs are refreshed as conditions change so planning "
        "remains accurate throughout the day."
    )
    for label, body in [
        ("Reduced fuel costs:", "Shorter routes and fewer detours lower spend."),
        ("Better vehicle utilization:", "Balance workloads and reduce empty miles."),
        ("Faster deliveries:", "Traffic-aware routing and smarter stop sequencing."),
        ("Improved planning:", "Reliable ETAs help staffing and customer commitments."),
    ]:
        add_bullet(doc, label, body)

    # ════════════════════════════════════════════════════════════════════════
    # SECTION 6 – Trust, Reviews & Quality Control
    # ════════════════════════════════════════════════════════════════════════
    add_section_heading(doc, "6.", "Trust, Reviews & Quality Control")
    add_body(
        doc,
        "A structured ratings and reviews program strengthens accountability and helps improve service "
        "quality over time."
    )
    for label, body in [
        ("Accountability:", "Performance is tracked against clear delivery expectations."),
        ("Service quality:", "Highlight top carriers and address repeated issues."),
        ("Trust and transparency:", "Shared feedback builds confidence across partners."),
        ("Performance monitoring:", "Scorecards track on-time rates, damage, and responsiveness."),
    ]:
        add_bullet(doc, label, body)

    # ════════════════════════════════════════════════════════════════════════
    # SECTION 7 – Additional Capabilities
    # ════════════════════════════════════════════════════════════════════════
    add_section_heading(doc, "7.", "Additional Capabilities")
    add_body(
        doc,
        "Supporting features make the platform easy to operate day to day and scalable across the "
        "business."
    )
    for label, body in [
        ("Role-based access:", "Each team sees only what they need."),
        ("Operational dashboards:", "Track volume, cost, and service KPIs."),
        ("System integrations:", "Connect finance, warehouse, and ERP tools."),
        ("Multi-branch support:", "Standardize processes across locations."),
    ]:
        add_bullet(doc, label, body)

    # ════════════════════════════════════════════════════════════════════════
    # SECTION 8 – Security & Reliability
    # ════════════════════════════════════════════════════════════════════════
    add_section_heading(doc, "8.", "Security & Reliability")
    add_body(
        doc,
        "Security and reliability are built in so the platform can be trusted for daily operations."
    )
    for label, body in [
        ("Secure access controls:", "Protect sensitive shipment and customer data."),
        ("Audit trails:", "Maintain a clear record of key actions and updates."),
        ("Reliable availability:", "Designed for daily use with backups and monitoring."),
        ("Deployment flexibility:", "Cloud or on-premise options based on policy."),
    ]:
        add_bullet(doc, label, body)

    # ════════════════════════════════════════════════════════════════════════
    # SECTION 9 – Implementation & Support
    # ════════════════════════════════════════════════════════════════════════
    add_section_heading(doc, "9.", "Implementation & Support")
    add_body(
        doc,
        "We provide a structured rollout that delivers value quickly and minimizes operational risk."
    )
    for label, body in [
        ("Discovery & planning:", "Confirm workflows, lanes, and carrier network needs."),
        ("Configuration & training:", "Set up workflows and onboard teams."),
        ("Pilot launch:", "Prove value with a controlled rollout."),
        ("Ongoing support:", "Continuous improvement and operational assistance."),
    ]:
        add_bullet(doc, label, body)

    # ════════════════════════════════════════════════════════════════════════
    # SECTION 10 – Why This Platform
    # ════════════════════════════════════════════════════════════════════════
    add_section_heading(doc, "10.", "Why This Platform")
    add_body(
        doc,
        "Traditional logistics operations often depend on phone calls, spreadsheets, and manual "
        "follow-ups. This leads to delays, inconsistent data, and limited visibility."
    )

    add_sub_heading(doc, "Traditional Operations")
    for label, body in [
        ("Phone calls and emails:", "Status updates depend on people being available."),
        ("Spreadsheets:", "Data is fragmented and quickly out of date."),
        ("Manual coordination:", "Assignment and follow-up take time and increase errors."),
    ]:
        add_bullet(doc, label, body)

    add_sub_heading(doc, "With the Proposed Platform")
    for label, body in [
        ("Centralized operations:", "All shipments, carriers, and updates in one place."),
        ("Real-time visibility:", "Live tracking and alerts reduce surprises."),
        ("Better decision making:", "Reliable data supports faster, smarter choices."),
        ("Improved efficiency:", "Less manual work and clearer accountability."),
    ]:
        add_bullet(doc, label, body)

    add_body(
        doc,
        "The result is a more predictable logistics operation with lower costs and stronger service "
        "quality for customers."
    )

    # ════════════════════════════════════════════════════════════════════════
    # SECTION 11 – Development Approach
    # ════════════════════════════════════════════════════════════════════════
    add_section_heading(doc, "11.", "Development Approach")
    add_body(
        doc,
        "We recommend a phased implementation so the business sees value early while reducing delivery "
        "risk. Each phase builds on the previous one without disrupting day-to-day operations."
    )

    add_sub_heading(doc, "Phase 1 – Core Logistics Platform")
    for item in [
        "User management",
        "Security",
        "Shipment creation",
        "Shipment posting",
        "Carrier matching",
        "Shipment assignment",
        "Driver management",
        "Shipment status tracking",
        "Ratings and reviews",
    ]:
        add_bullet(doc, item, "")

    add_sub_heading(doc, "Phase 2 – Live Tracking")
    for item in [
        "GPS integration",
        "Smartphone-based tracking",
        "Real-time location monitoring",
        "Tracking dashboards",
    ]:
        add_bullet(doc, item, "")

    add_sub_heading(doc, "Phase 3 – Route Optimization")
    for item in [
        "Route planning",
        "ETA prediction",
        "Route monitoring",
    ]:
        add_bullet(doc, item, "")

    add_sub_heading(doc, "Phase 4 – Future Enhancements")
    for item in [
        "Payment integration",
        "Proof of delivery",
        "Advanced analytics",
        "Fleet management integration",
        "Warehouse integration",
        "AI-assisted forecasting",
    ]:
        add_bullet(doc, item, "")

    add_sub_heading(doc, "Development Philosophy")
    add_body(
        doc,
        "The platform is designed to solve immediate operational challenges while creating a scalable "
        "foundation for future growth. Each phase adds value without requiring a full redesign, so the "
        "business can evolve its capabilities over time with confidence."
    )

    doc.add_paragraph()
    add_horizontal_rule(doc)
    closing = doc.add_paragraph()
    closing.alignment = WD_ALIGN_PARAGRAPH.CENTER
    cr = closing.add_run("End of Proposal")
    cr.italic = True
    cr.font.size = Pt(9)
    cr.font.color.rgb = RGBColor(0x88, 0x88, 0x88)

    return doc


# ---------------------------------------------------------------------------
# Entry point
# ---------------------------------------------------------------------------

if __name__ == "__main__":
    output_path = "Logistics_Platform_Proposal.docx"
    doc = build_document()
    doc.save(output_path)
    print(f"✅  Document saved: {output_path}")
