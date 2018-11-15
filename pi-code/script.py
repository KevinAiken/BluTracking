from bluepy.btle import Scanner, DefaultDelegate
import requests
import datetime
import time

class ScanDelegate(DefaultDelegate):
    def __init__(self):
        DefaultDelegate.__init__(self)

    def handleDiscovery(self, dev, isNewDev, isNewData):
        if isNewDev:
            print "Discovered device", dev.addr
        elif isNewData:
            print "Received new data from", dev.addr

websiteIP = "confidentialIP:8080" # spring service on server
#websiteIP = ":8080" 
headers = {'Content-Type': 'application/json', 'Cache-Control': 'no-cache'}

while True:
    #pull the device list
    payload = []
    get = requests.get("http://"+websiteIP+"/devices")
    print get.text
    scanner = Scanner().withDelegate(ScanDelegate())
    devices = scanner.scan(10.0)

    for dev in devices:
	print "Dev"
	print dev.addr
        for x in get.json():
	    print "btaddr line"
	    print x['btAddr']
            if x['btAddr'].lower() == dev.addr.lower():
                print("found a device")
		ts = time.time()
                payload.append(("{ \"btAddr\": \""+dev.addr+"\", \"time\": \""
		+datetime.datetime.fromtimestamp(ts).strftime('%Y-%m-%d %H:%M:%S')+"\"}").encode('ascii').replace("'", ""))
    print payload
    post = requests.post("http://"+websiteIP+"/logs", data=str(payload).replace("'", ""), headers=headers)
    time.sleep(10)


