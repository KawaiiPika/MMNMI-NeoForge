import re
with open('build.gradle', 'r') as f:
    content = f.read()

content = content.replace('server()\n            systemProperty', 'systemProperty')

with open('build.gradle', 'w') as f:
    f.write(content)
