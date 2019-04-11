SELECT
language,
 name,
 level
FROM developers_skills
LEFT JOIN developers ON developers_id = developers_skills.dev_id
LEFT JOIN skills ON developers_skills.skill_id = skills_id
WHERE language="Java";
