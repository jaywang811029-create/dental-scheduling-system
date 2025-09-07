
select
    rs1_0.date,
    rs1_0.region,
    rs1_0.shift_type,
    rs1_0.chairside_name1,
    rs1_0.chairside_name2,
    rs1_0.chairside_name3,
    rs1_0.doctor_name1 1,
    rs1_0.doctor_name2,
    rs1_0.doctor_name3,
    rs1_0.floater_name1,
    rs1_0.floater_name2,
    rs1_0.floater_name3,
    rs1_0.front_desk1,
    rs1_0.front_desk2,
    rs1_0.front_desk3,
    rs1_0.support_name1,
    rs1_0.support_name2
from
    result_schedule rs1_0
where
    rs1_0.date between ?
    and ?
    and rs1_0.region = ?