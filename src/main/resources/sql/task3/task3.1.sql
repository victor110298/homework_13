SELECT
SUM(salary),
project_name
FROM developers_projects
LEFT JOIN developers ON developers_id = developers_projects.dev_id
LEFT JOIN projects ON developers_projects.proj_id = projects_id
GROUP BY proj_id
ORDER BY sum(salary);
