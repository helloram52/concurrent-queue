import re, json

filename = "crap.log"
# Extract thread id, start/end of an operation, operation itself
threadSize = 8
result = { str(x): [] for x in range(1, threadSize + 1)}
"""
LEt's hsave the below structure
{
  "thread1" : [
    { range: [start, end], operation: "search(5): false"}
  ],
  ....
}
"""
content = []
with open(filename) as f:
  content = f.readlines()


for x in content:
  text = re.sub(r'.* Nanotime: ', r'', x)
  nanotime, threadName, operationID, operation = "", "", 0, ""

  if 'Invoking' in text:

    m = re.search(r'(\d+) INFO : pool-\d+-thread-(\d+) : Invoking OperationID: (\d+)', text)
    nanotime, threadName, operationID = m.group(1), m.group(2), m.group(3)
    # print "nanotime: {}, threadName: {}, operationid: {}".format(nanotime, threadName, operationID)

    newHash = {
      "range" : [nanotime],
      "operation" : ""
    }
    result[threadName].append(newHash)
  else:

    m = re.search(r'(\d+) INFO : pool-\d+-thread-(\d+) : OperationID: (\d+) (.*)$', text)
    nanotime, threadName, operationID, operation = m.group(1), m.group(2), m.group(3), m.group(4)
    # print "nanotime: {}, threadName: {}, operationid: {}, operation: {}".format(nanotime, threadName, operationID, operation)
    result[threadName][-1]["range"].append(nanotime)
    result[threadName][-1]["operation"] = operation

print json.dumps(result, sort_keys=True, indent=2)
print "printing CSV"

print "ThreadName, rangeStart, rangeEnd, label"
for threadName in result.keys():
  for row in result[threadName]:
    # print '[{}, {}, {}, "{}"],'.format(threadName, row["range"][0], row["range"][1], row["operation"])
    print '["{}", {}, {}],'.format(threadName, row["range"][0], row["range"][1])
