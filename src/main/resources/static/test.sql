select
    a1_0.id,
    a1_0.boos_hours,
    a1_0.is_chairside,
    a1_0.is_floater,
    a1_0.is_front_desk,
    a1_0.name,
    a1_0.precedence,
    a1_0.region,
    a1_0.total_hours
from
    assistants a1_0
    join weekly_schedule ws1_0 on a1_0.id = ws1_0.id
    left join leave_schedule ls1_0 on a1_0.id = ls1_0.assistant_id
    and ls1_0.is_leave <> true
where
    ls1_0.assistant_id is null
    and ws1_0.saturday_work_time = ?
    and a1_0.region like ? escape ''
    and a1_0.total_hours < 170
order by
    a1_0.precedence desc,
    a1_0.total_hours