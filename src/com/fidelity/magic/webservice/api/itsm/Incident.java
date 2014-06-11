package com.fidelity.magic.webservice.api.itsm;

import java.util.Date;
import java.util.List;

import javax.xml.bind.annotation.XmlRootElement;

import org.codehaus.jackson.map.annotate.JsonSerialize;

/**
 * incident object for POC
 * @author A322448
 *
 */
@XmlRootElement (name="incident")
@JsonSerialize(include=JsonSerialize.Inclusion.NON_NULL)
public class Incident {
			
	private String change_timestamp; //"change_timestamp":"2014-01-23 12:56:18"
	private List<Journal> journal;
	
	
	private boolean active; //<active>false</active>
    private String activity_due; //<activity_due/>
    private String approval; //<approval/>
    private String approval_history; //<approval_history/>
    private String approval_set; //<approval_set/>
    public List<Journal> getJournal() {
		return journal;
	}
	public void setJournal(List<Journal> journal) {
		this.journal = journal;
	}

	private String assigned_to; //<assigned_to/>
    private String assignment_group; //<assignment_group/>
    private Date business_duration; //<business_duration>1970-01-01 03:40:03</business_duration>
    private String business_stc; //<business_stc>13203</business_stc>
    private Date calendar_duration; //<calendar_duration>1970-01-02 00:00:15</calendar_duration>
    private String calendar_stc; //<calendar_stc>86415</calendar_stc>
    private String caller_id; //<caller_id/>
    private String category; //<category>network</category>
    private String caused_by; //<caused_by/>
    private String child_incidents; //<child_incidents/>
    private String close_code; //<close_code>Solved (Work Around)</close_code>
    private String close_notes; //<close_notes/>
    private Date closed_at; //<closed_at>2013-09-19 23:10:06</closed_at>
    private String closed_by; //<closed_by/>
    
    //TODO
    private String cmdb_ci; //<cmdb_ci display_value="MailServerUS">b0c4030ac0a800090152e7a4564ca36c</cmdb_ci>
    private String comments; //<comments/>
    private String comments_and_work_notes; //<comments_and_work_notes/>
    private String company; //<company/>
    private String contact_type; //<contact_type/>
    private String correlation_display; //<correlation_display/>
    private String correlation_id; //<correlation_id/>
    private String delivery_plan; //<delivery_plan/>
    private String delivery_task; //<delivery_task/>
    private String description; //<description>User can't access email on mail.company.com.</description>
    private Date due_date; //<due_date/>
    private String escalation; //<escalation>0</escalation>
    private String expected_start; //<expected_start/>
    private String follow_up; //<follow_up/>
    private String group_list; //<group_list/>
    private String impact; //<impact>1</impact>
    private String incident_state; //<incident_state>7</incident_state>
    private boolean knowledge; //<knowledge>false</knowledge>
    //TODO
    private String location; //<location display_value="Oklahoma">1083361cc611227501b682158cabf646</location>
    private boolean made_sla; //<made_sla>false</made_sla>
    private String notify; //<notify>1</notify>
    private String number; //<number>INC0000001</number>
    private Date opened_at; //<opened_at>2013-09-18 23:09:51</opened_at>
    private String opened_by; //<opened_by/>
    private String order; //<order/>
    private String parent; //<parent/>
    private String parent_incident; //<parent_incident/>
    private String priority; //<priority>2</priority>
    
    //TODO
    private String problem_id; //<problem_id display_value="PRB0000007">9d3a266ac6112287004e37fb2ceb0133</problem_id>
    private String reassignment_count; //<reassignment_count/>
    private String reopen_count; //<reopen_count/>
    private Date resolved_at; //<resolved_at/>
    private String resolved_by; //<resolved_by/>
    private String rfc; //<rfc/>
    private String severity; //<severity>1</severity>
    private String short_description; //<short_description>Can't read email</short_description>
    private String skills; //<skills/>
    private Date sla_due; //<sla_due/>
    private String state; //<state>1</state>
    private String subcategory; //<subcategory/>
    private String sys_class_name; //<sys_class_name>incident</sys_class_name>
    private String sys_created_by; //<sys_created_by>pat</sys_created_by>
    private Date sys_created_on; //<sys_created_on>2012-04-19 18:24:13</sys_created_on>
    private String sys_domain; //<sys_domain>global</sys_domain>
    private String sys_id; //<sys_id>9c573169c611228700193229fff72400</sys_id>
    private String sys_mod_count; //<sys_mod_count>19</sys_mod_count>
    private String sys_updated_by; //<sys_updated_by>glide.maint</sys_updated_by>
    private Date sys_updated_on; //<sys_updated_on>2014-01-09 01:20:56</sys_updated_on>
    private String time_worked; //<time_worked/>
    private String u_group_watch_list; //<u_group_watch_list/>
    private String upon_approval; //<upon_approval/>
    private String upon_reject; //<upon_reject/>
    private String urgency; //<urgency>1</urgency>
    private String user_input; //<user_input/>
    private String watch_list; //<watch_list/>
    private String work_end; //<work_end/>
    private String work_notes; //<work_notes/>
    private String work_notes_list; //<work_notes_list/>
    private String work_start; //<work_start/>
    
    

	public boolean isActive() {
		return active;
	}
	public void setActive(boolean active) {
		this.active = active;
	}
	public String getActivity_due() {
		return activity_due;
	}
	public void setActivity_due(String activity_due) {
		this.activity_due = activity_due;
	}
	public String getApproval() {
		return approval;
	}
	public void setApproval(String approval) {
		this.approval = approval;
	}
	public String getApproval_history() {
		return approval_history;
	}
	public void setApproval_history(String approval_history) {
		this.approval_history = approval_history;
	}
	public String getApproval_set() {
		return approval_set;
	}
	public void setApproval_set(String approval_set) {
		this.approval_set = approval_set;
	}
	public String getAssigned_to() {
		return assigned_to;
	}
	public void setAssigned_to(String assigned_to) {
		this.assigned_to = assigned_to;
	}
	public String getAssignment_group() {
		return assignment_group;
	}
	public void setAssignment_group(String assignment_group) {
		this.assignment_group = assignment_group;
	}
	public Date getBusiness_duration() {
		return business_duration;
	}
	public void setBusiness_duration(Date business_duration) {
		this.business_duration = business_duration;
	}
	public String getBusiness_stc() {
		return business_stc;
	}
	public void setBusiness_stc(String business_stc) {
		this.business_stc = business_stc;
	}
	public Date getCalendar_duration() {
		return calendar_duration;
	}
	public void setCalendar_duration(Date calendar_duration) {
		this.calendar_duration = calendar_duration;
	}
	public String getCalendar_stc() {
		return calendar_stc;
	}
	public void setCalendar_stc(String calendar_stc) {
		this.calendar_stc = calendar_stc;
	}
	public String getCaller_id() {
		return caller_id;
	}
	public void setCaller_id(String caller_id) {
		this.caller_id = caller_id;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public String getCaused_by() {
		return caused_by;
	}
	public void setCaused_by(String caused_by) {
		this.caused_by = caused_by;
	}
	public String getChild_incidents() {
		return child_incidents;
	}
	public void setChild_incidents(String child_incidents) {
		this.child_incidents = child_incidents;
	}
	public String getClose_code() {
		return close_code;
	}
	public void setClose_code(String close_code) {
		this.close_code = close_code;
	}
	public String getClose_notes() {
		return close_notes;
	}
	public void setClose_notes(String close_notes) {
		this.close_notes = close_notes;
	}
	public Date getClosed_at() {
		return closed_at;
	}
	public void setClosed_at(Date closed_at) {
		this.closed_at = closed_at;
	}
	public String getClosed_by() {
		return closed_by;
	}
	public void setClosed_by(String closed_by) {
		this.closed_by = closed_by;
	}
	public String getCmdb_ci() {
		return cmdb_ci;
	}
	public void setCmdb_ci(String cmdb_ci) {
		this.cmdb_ci = cmdb_ci;
	}
	public String getComments() {
		return comments;
	}
	public void setComments(String comments) {
		this.comments = comments;
	}
	public String getComments_and_work_notes() {
		return comments_and_work_notes;
	}
	public void setComments_and_work_notes(String comments_and_work_notes) {
		this.comments_and_work_notes = comments_and_work_notes;
	}
	public String getCompany() {
		return company;
	}
	public void setCompany(String company) {
		this.company = company;
	}
	public String getContact_type() {
		return contact_type;
	}
	public void setContact_type(String contact_type) {
		this.contact_type = contact_type;
	}
	public String getCorrelation_display() {
		return correlation_display;
	}
	public void setCorrelation_display(String correlation_display) {
		this.correlation_display = correlation_display;
	}
	public String getCorrelation_id() {
		return correlation_id;
	}
	public void setCorrelation_id(String correlation_id) {
		this.correlation_id = correlation_id;
	}
	public String getDelivery_plan() {
		return delivery_plan;
	}
	public void setDelivery_plan(String delivery_plan) {
		this.delivery_plan = delivery_plan;
	}
	public String getDelivery_task() {
		return delivery_task;
	}
	public void setDelivery_task(String delivery_task) {
		this.delivery_task = delivery_task;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Date getDue_date() {
		return due_date;
	}
	public void setDue_date(Date due_date) {
		this.due_date = due_date;
	}
	public String getEscalation() {
		return escalation;
	}
	public void setEscalation(String escalation) {
		this.escalation = escalation;
	}
	public String getExpected_start() {
		return expected_start;
	}
	public void setExpected_start(String expected_start) {
		this.expected_start = expected_start;
	}
	public String getFollow_up() {
		return follow_up;
	}
	public void setFollow_up(String follow_up) {
		this.follow_up = follow_up;
	}
	public String getGroup_list() {
		return group_list;
	}
	public void setGroup_list(String group_list) {
		this.group_list = group_list;
	}
	public String getImpact() {
		return impact;
	}
	public void setImpact(String impact) {
		this.impact = impact;
	}
	public String getIncident_state() {
		return incident_state;
	}
	public void setIncident_state(String incident_state) {
		this.incident_state = incident_state;
	}
	public boolean isKnowledge() {
		return knowledge;
	}
	public void setKnowledge(boolean knowledge) {
		this.knowledge = knowledge;
	}
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public boolean isMade_sla() {
		return made_sla;
	}
	public void setMade_sla(boolean made_sla) {
		this.made_sla = made_sla;
	}
	public String getNotify() {
		return notify;
	}
	public void setNotify(String notify) {
		this.notify = notify;
	}
	public String getNumber() {
		return number;
	}
	public void setNumber(String number) {
		this.number = number;
	}
	public Date getOpened_at() {
		return opened_at;
	}
	public void setOpened_at(Date opened_at) {
		this.opened_at = opened_at;
	}
	public String getOpened_by() {
		return opened_by;
	}
	public void setOpened_by(String opened_by) {
		this.opened_by = opened_by;
	}
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		this.order = order;
	}
	public String getParent() {
		return parent;
	}
	public void setParent(String parent) {
		this.parent = parent;
	}
	public String getParent_incident() {
		return parent_incident;
	}
	public void setParent_incident(String parent_incident) {
		this.parent_incident = parent_incident;
	}
	public String getPriority() {
		return priority;
	}
	public void setPriority(String priority) {
		this.priority = priority;
	}
	public String getProblem_id() {
		return problem_id;
	}
	public void setProblem_id(String problem_id) {
		this.problem_id = problem_id;
	}
	public String getReassignment_count() {
		return reassignment_count;
	}
	public void setReassignment_count(String reassignment_count) {
		this.reassignment_count = reassignment_count;
	}
	public String getReopen_count() {
		return reopen_count;
	}
	public void setReopen_count(String reopen_count) {
		this.reopen_count = reopen_count;
	}
	public Date getResolved_at() {
		return resolved_at;
	}
	public void setResolved_at(Date resolved_at) {
		this.resolved_at = resolved_at;
	}
	public String getResolved_by() {
		return resolved_by;
	}
	public void setResolved_by(String resolved_by) {
		this.resolved_by = resolved_by;
	}
	public String getRfc() {
		return rfc;
	}
	public void setRfc(String rfc) {
		this.rfc = rfc;
	}
	public String getSeverity() {
		return severity;
	}
	public void setSeverity(String severity) {
		this.severity = severity;
	}
	public String getShort_description() {
		return short_description;
	}
	public void setShort_description(String short_description) {
		this.short_description = short_description;
	}
	public String getSkills() {
		return skills;
	}
	public void setSkills(String skills) {
		this.skills = skills;
	}
	public Date getSla_due() {
		return sla_due;
	}
	public void setSla_due(Date sla_due) {
		this.sla_due = sla_due;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getSubcategory() {
		return subcategory;
	}
	public void setSubcategory(String subcategory) {
		this.subcategory = subcategory;
	}
	public String getSys_class_name() {
		return sys_class_name;
	}
	public void setSys_class_name(String sys_class_name) {
		this.sys_class_name = sys_class_name;
	}
	public String getSys_created_by() {
		return sys_created_by;
	}
	public void setSys_created_by(String sys_created_by) {
		this.sys_created_by = sys_created_by;
	}
	public Date getSys_created_on() {
		return sys_created_on;
	}
	public void setSys_created_on(Date sys_created_on) {
		this.sys_created_on = sys_created_on;
	}
	public String getSys_domain() {
		return sys_domain;
	}
	public void setSys_domain(String sys_domain) {
		this.sys_domain = sys_domain;
	}
	public String getSys_id() {
		return sys_id;
	}
	public void setSys_id(String sys_id) {
		this.sys_id = sys_id;
	}
	public String getSys_mod_count() {
		return sys_mod_count;
	}
	public void setSys_mod_count(String sys_mod_count) {
		this.sys_mod_count = sys_mod_count;
	}
	public String getSys_updated_by() {
		return sys_updated_by;
	}
	public void setSys_updated_by(String sys_updated_by) {
		this.sys_updated_by = sys_updated_by;
	}
	public Date getSys_updated_on() {
		return sys_updated_on;
	}
	public void setSys_updated_on(Date sys_updated_on) {
		this.sys_updated_on = sys_updated_on;
	}
	public String getTime_worked() {
		return time_worked;
	}
	public void setTime_worked(String time_worked) {
		this.time_worked = time_worked;
	}
	public String getU_group_watch_list() {
		return u_group_watch_list;
	}
	public void setU_group_watch_list(String u_group_watch_list) {
		this.u_group_watch_list = u_group_watch_list;
	}
	public String getUpon_approval() {
		return upon_approval;
	}
	public void setUpon_approval(String upon_approval) {
		this.upon_approval = upon_approval;
	}
	public String getUpon_reject() {
		return upon_reject;
	}
	public void setUpon_reject(String upon_reject) {
		this.upon_reject = upon_reject;
	}
	public String getUrgency() {
		return urgency;
	}
	public void setUrgency(String urgency) {
		this.urgency = urgency;
	}
	public String getUser_input() {
		return user_input;
	}
	public void setUser_input(String user_input) {
		this.user_input = user_input;
	}
	public String getWatch_list() {
		return watch_list;
	}
	public void setWatch_list(String watch_list) {
		this.watch_list = watch_list;
	}
	public String getWork_end() {
		return work_end;
	}
	public void setWork_end(String work_end) {
		this.work_end = work_end;
	}
	public String getWork_notes() {
		return work_notes;
	}
	public void setWork_notes(String work_notes) {
		this.work_notes = work_notes;
	}
	public String getWork_notes_list() {
		return work_notes_list;
	}
	public void setWork_notes_list(String work_notes_list) {
		this.work_notes_list = work_notes_list;
	}
	public String getWork_start() {
		return work_start;
	}
	public void setWork_start(String work_start) {
		this.work_start = work_start;
	}
	public String getChange_timestamp() {
		return change_timestamp;
	}
	public void setChange_timestamp(String change_timestamp) {
		this.change_timestamp = change_timestamp;
	}

	public void update(Incident value)
	{
		if (value!=null && this != value)
		{
			if (value.getAssignment_group() !=null)
				setAssignment_group(value.getAssignment_group());
			
			if (value.getChange_timestamp() !=null)
				setChange_timestamp(value.getChange_timestamp());
			
			if (value.getPriority() !=null)
				setPriority(value.getPriority());
			
			if (value.getUrgency() !=null)
				setUrgency(value.getUrgency());
			
			if (value.getDescription() !=null)
				setDescription(value.getDescription());
			
			if (value.getShort_description() !=null)
				setShort_description(value.getShort_description());
			
		}
	}
    
}
