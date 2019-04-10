SELECT
MIN(cost),
project_name
FROM projects
GROUP BY project_name
ORDER BY sum(cost)
LIMIT 1;
