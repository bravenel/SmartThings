/**
 *	Scene Controller
 *
 *	Author: SmartThings, modified by Bruce Ravenel
 *	Date: 2015-05-07
 */
definition(
    name: "Scene Controller",
    namespace: "",
    author: "SmartThings, Bruce Ravenel",
    description: "Set Scenes with the Aeon Labs Minimote",
    category: "My Apps",
    iconUrl: "https://s3.amazonaws.com/smartapp-icons/MyApps/Cat-MyApps.png",
    iconX2Url: "https://s3.amazonaws.com/smartapp-icons/MyApps/Cat-MyApps@2x.png"
)

preferences {
	page(name: "selectButton")
	page(name: "configureButton1")
	page(name: "configureButton2")
	page(name: "configureButton3")
	page(name: "configureButton4")
	
	page(name: "timeIntervalInput", title: "Only during a certain time") {
		section {
			input "starting", "time", title: "Starting", required: false
			input "ending", "time", title: "Ending", required: false
		}
	}
    
//    page(name: "dateIntervalInput", title: "Only between these dates") {
//    	section {
//        	input "startingdate", "date", title: "Starting Date   mm-dd", required: false
//            input "endingdate", "date", title: "Ending Date   mm-dd", required: false
//        }
//    }
}

def selectButton() {
	dynamicPage(name: "selectButton", title: "First, select your button device", nextPage: "configureButton1", uninstall: configured()) {
		section {
			input "buttonDevice", "capability.button", title: "MiniMote", multiple: false, required: false
		}
		
		section(title: "More options", hidden: hideOptionsSection(), hideable: true) { 
			
			def timeLabel = timeIntervalLabel()

			href "timeIntervalInput", title: "Only during a certain time", description: timeLabel ?: "Tap to set", state: timeLabel ? "complete" : null

			input "days", "enum", title: "Only on certain days of the week", multiple: true, required: false,
				options: ["Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"]

			input "modes", "mode", title: "Only when mode is", multiple: true, required: false
            
//            href "dateIntervalInput", title: "Only between two dates", description: dateLabel ?: "Tap to set", state: dateLabel ? "complete" : null
		}
        section() {
        	label title: "Assign a name:", required: false
        }
	}
}

def configureButton1() {
	dynamicPage(name: "configureButton1", title: "Set up the first button",
		nextPage: "configureButton2", uninstall: configured(), getButtonSections(1))
}
def configureButton2() {
	dynamicPage(name: "configureButton2", title: "Set up the second button",
		nextPage: "configureButton3", uninstall: configured(), getButtonSections(2))
}

def configureButton3() {
	dynamicPage(name: "configureButton3", title: "Set up the third button",
		nextPage: "configureButton4", uninstall: configured(), getButtonSections(3))
}
def configureButton4() {
	dynamicPage(name: "configureButton4", title: "Set up the fourth button",
		install: true, uninstall: true, getButtonSections(4))
}

def getButtonSections(buttonNumber) {
	return {
    	section("Button number $buttonNumber") { } //correct for ST bug with page title
		section("Lights to Toggle") {
			input "lights_${buttonNumber}_pushed", "capability.switch", title: "Pushed", multiple: true, required: false
			input "lights_${buttonNumber}_held", "capability.switch", title: "Held", multiple: true, required: false
		}
		section("Lights to Turn On") {
			input "lightOn_${buttonNumber}_pushed", "capability.switch", title: "Pushed", multiple: true, required: false
			input "lightOn_${buttonNumber}_held", "capability.switch", title: "Held", multiple: true, required: false
		}
        section("Lights to Turn Off") {
			input "lightOff_${buttonNumber}_pushed", "capability.switch", title: "Pushed", multiple: true, required: false
			input "lightOff_${buttonNumber}_held", "capability.switch", title: "Held", multiple: true, required: false
		}
        section("Lights to Dim Level 1") {
			input "lightDim_${buttonNumber}_pushed", "capability.switchLevel", title: "Pushed", multiple: true, required: false
			input "lightVal_${buttonNumber}_pushed", "number", title: "Dim Level 1", multiple: false, required: false, description: "0 to 99"
			input "lightDim_${buttonNumber}_held", "capability.switchLevel", title: "Held", multiple: true, required: false
			input "lightVal_${buttonNumber}_held", "number", title: "Dim Level 1", multiple: false, required: false, description: "0 to 99"
		}
        section("Lights to Dim Level 2") {
			input "lightD2m_${buttonNumber}_pushed", "capability.switchLevel", title: "Pushed", multiple: true, required: false
			input "lightV2l_${buttonNumber}_pushed", "number", title: "Dim Level 2", multiple: false, required: false, description: "0 to 99"
			input "lightD2m_${buttonNumber}_held", "capability.switchLevel", title: "Held", multiple: true, required: false
			input "lightV2l_${buttonNumber}_held", "number", title: "Dim Level 2", multiple: false, required: false, description: "0 to 99"
		}
        section("Fan to Adjust Speed") {
			input "autoFanAdjust_${buttonNumber}_pushed", "capability.switchLevel", title: "Pushed", multiple: false, required: false
			input "autoFanAdjust_${buttonNumber}_held", "capability.switchLevel", title: "Held", multiple: false, required: false
        }
        section("Shades to Adjust") {
        	input "shadesAdjust_${buttonNumber}_pushed", "capability.doorControl", title: "Pushed", multiple: false, required: false
			input "shadesAdjust_${buttonNumber}_held", "capability.doorControl", title: "Held", multiple: false, required: false
        }
//		section("Locks") {
//			input "locks_${buttonNumber}_pushed", "capability.lock", title: "Pushed", multiple: true, required: false
//			input "locks_${buttonNumber}_held", "capability.lock", title: "Held", multiple: true, required: false
//		}
//		section("Sonos") {
//			input "sonos_${buttonNumber}_pushed", "capability.musicPlayer", title: "Pushed", multiple: true, required: false
//			input "sonos_${buttonNumber}_held", "capability.musicPlayer", title: "Held", multiple: true, required: false
//		}
		section("Mode to Set") {
			input "mode_${buttonNumber}_pushed", "mode", title: "Pushed", required: false
			input "mode_${buttonNumber}_held", "mode", title: "Held", required: false
		}
		def phrases = location.helloHome?.getPhrases()*.label
		if (phrases) {
			section("Hello Home Action to Take") {
				log.trace phrases
				input "phrase_${buttonNumber}_pushed", "enum", title: "Pushed", required: false, options: phrases
				input "phrase_${buttonNumber}_held", "enum", title: "Held", required: false, options: phrases
			}
		}
        section("Associate a Momentary Button"){
        	input "virtB_${buttonNumber}_pushed","capability.momentary",title: "Pushed", required: false
            input "virtB_${buttonNumber}_held","capability.momentary",title: "Held", required: false
        }
	}
}

def installed() {
	initialize()
}

def updated() {
	unsubscribe()
	initialize()
}

def initialize() {
	subscribe(buttonDevice, "button", buttonEvent)
    subscribe(virtB_1_pushed,"momentary.pushed",fakebutton1Event)
    subscribe(virtB_2_pushed,"momentary.pushed",fakebutton2Event)
    subscribe(virtB_3_pushed,"momentary.pushed",fakebutton3Event)
    subscribe(virtB_4_pushed,"momentary.pushed",fakebutton4Event)
    subscribe(virtB_1_held,"momentary.pushed",fakebutton1hEvent)
    subscribe(virtB_2_held,"momentary.pushed",fakebutton2hEvent)
    subscribe(virtB_3_held,"momentary.pushed",fakebutton3hEvent)
    subscribe(virtB_4_held,"momentary.pushed",fakebutton4hEvent)
    state.goingUp = true
    state.lastshadesUp = true
//    log.debug "Scene Controller Dates are $startingdate and $endingdate"
}

def configured() {
	return buttonDevice || buttonConfigured(1) || buttonConfigured(2) || buttonConfigured(3) || buttonConfigured(4)
}

def buttonConfigured(idx) {
	return settings["lights_$idx_pushed"] ||
    	settings["lightOn_$idx_pushed"] ||
    	settings["lightOff_$idx_pushed"] ||
        settings["lightDim_$idx_pushed"] ||
        settings["lightVal_$idx_pushed"] ||
        settings["lightD2m_$idx_pushed"] ||
        settings["lightV2l_$idx_pushed"] ||
        settings["autoFanAdjust_$idx_pushed"] ||
        settings["shadesAdjust_$idx_pushed"] ||
		settings["locks_$idx_pushed"] ||
		settings["sonos_$idx_pushed"] ||
		settings["mode_$idx_pushed"]
}

def fakebutton1Event(evt) {
    executeHandlers(1, "pushed")
}

def fakebutton2Event(evt) {
    executeHandlers(2, "pushed")
}

def fakebutton3Event(evt) {
    executeHandlers(3, "pushed")
}

def fakebutton4Event(evt) {
    executeHandlers(4, "pushed")
}

def fakebutton1hEvent(evt) {
    executeHandlers(1, "held")
}

def fakebutton2hEvent(evt) {
    executeHandlers(2, "held")
}

def fakebutton3hEvent(evt) {
    executeHandlers(3, "held")
}

def fakebutton4hEvent(evt) {
    executeHandlers(4, "held")
}

def buttonEvent(evt){
	if(allOk) {
		def buttonNumber = evt.data // why doesn't jsonData work? always returning [:]
		def value = evt.value
		log.debug "buttonEvent: $evt.name = $evt.value ($evt.data)"
		log.debug "button: $buttonNumber, value: $value"
	
		def recentEvents = buttonDevice.eventsSince(new Date(now() - 3000)).findAll{it.value == evt.value && it.data == evt.data}
		log.debug "Found ${recentEvents.size()?:0} events in past 3 seconds"
	
		if(recentEvents.size <= 1){
			switch(buttonNumber) {
				case ~/.*1.*/:
					executeHandlers(1, value)
					break
				case ~/.*2.*/:
					executeHandlers(2, value)
					break
				case ~/.*3.*/:
					executeHandlers(3, value)
					break
				case ~/.*4.*/:
					executeHandlers(4, value)
					break
			}
		} else {
			log.debug "Found recent button press events for $buttonNumber with value $value"
		}
	}
}

def executeHandlers(buttonNumber, value) {
	log.debug "executeHandlers: $buttonNumber - $value"

	def lights = find('lights', buttonNumber, value)
	if (lights) toggle(lights)

	def lights1 = find('lightOn', buttonNumber, value)
	if (lights1) turnOn(lights1)

	def lights2 = find('lightOff', buttonNumber, value)
	if (lights2) turnOff(lights2)

	def lights3 = find('lightDim', buttonNumber, value)
    def dimval3 = find('lightVal', buttonNumber, value)
	if (lights3) turnDim(lights3,dimval3)

	def lights4 = find('lightD2m', buttonNumber, value)
    def dimval4 = find('lightV2l', buttonNumber, value)
	if (lights4) turnDim(lights4,dimval4)

	def fan = find('autoFanAdjust', buttonNumber, value)
    if (fan) adjustFan(fan)
    
    def shade = find('shadesAdjust', buttonNumber, value)
    if (shade) adjustShade(shade)
    
	def locks = find('locks', buttonNumber, value)
	if (locks) toggle(locks)

	def sonos = find('sonos', buttonNumber, value)
	if (sonos) toggle(sonos)

	def mode = find('mode', buttonNumber, value)
	if (mode) changeMode(mode)

	def phrase = find('phrase', buttonNumber, value)
	if (phrase) location.helloHome.execute(phrase)
}

def find(type, buttonNumber, value) {
	def preferenceName = type + "_" + buttonNumber + "_" + value
	def pref = settings[preferenceName]
	if(pref) {
		log.debug "Found: $pref for $preferenceName"
	}

	return pref
}

def turnOn(devices) {
	log.debug "turnOn: $devices = ${devices*.currentSwitch}"

	devices.on()
}

def turnOff(devices) {
	log.debug "turnOff: $devices = ${devices*.currentSwitch}"

	devices.off()
}

def turnDim(devices, level) {
	log.debug "turnDim: $devices = ${devices*.currentSwitch}"

	devices.setLevel(level)
}

def adjustFan(device) { // for 3 speed ceiling fans
	log.debug "adjust: $device = ${device.currentLevel}"
    
    def currentLevel = device.currentLevel

    if(device.currentSwitch == 'off') {
    	device.setLevel(15)
        state.goingUp = true
    } else if (currentLevel < 34) {
    	if(state.goingUp) device.setLevel(50)
    	else device.off()
  	} else if (currentLevel < 67) {
  		if (state.goingUp) device.setLevel(90)
        else device.setLevel(15)
    } else {
    	device.setLevel(50)
      	state.goingUp = false
    }
}

def adjustShade(device) { // works with Aeon Motor Controller device type
	log.debug "shades: $device = ${device.currentMotor} state.lastUP = $state.lastshadesUp"

	if(device.currentMotor in ["up","down"]) {
    	state.lastshadesUp = device.currentMotor == "up"
    	device.stop()
    } else {
    	if(state.lastshadesUp) device.down()
        else device.up()
        state.lastshadesUp = !state.lastshadesUp
    }
}

def toggle(devices) {
	log.debug "toggle: $devices = ${devices*.currentSwitch}"

	if (devices*.currentSwitch.contains('on')) {
		devices.off()
	}
	else if (devices*.currentSwitch.contains('off')) {
		devices.on()
	}
	else if (devices*.currentLock.contains('locked')) {
		devices.unlock()
	}
	else if (devices*.currentLock.contains('unlocked')) {
		devices.lock()
	}
	else {
		devices.on()
	}
}

def changeMode(mode) {
	log.debug "changeMode: $mode, location.mode = $location.mode, location.modes = $location.modes"

	if (location.mode != mode && location.modes?.find { it.name == mode }) {
		setLocationMode(mode)
	}
}

// execution filter methods
private getAllOk() {
	modeOk && daysOk && timeOk
}

private getModeOk() {
	def result = !modes || modes.contains(location.mode)
	log.trace "modeOk = $result"
	result
}

private getDaysOk() {
	def result = true
	if (days) {
		def df = new java.text.SimpleDateFormat("EEEE")
		if (location.timeZone) {
			df.setTimeZone(location.timeZone)
		}
		else {
			df.setTimeZone(TimeZone.getTimeZone("America/New_York"))
		}
		def day = df.format(new Date())
		result = days.contains(day)
	}
	log.trace "daysOk = $result"
	result
}

private getTimeOk() {
	def result = true
	if (starting && ending) {
		def currTime = now()
		def start = timeToday(starting).time
		def stop = timeToday(ending).time
		result = start < stop ? currTime >= start && currTime <= stop : currTime <= stop || currTime >= start
	}
	log.trace "timeOk = $result"
	result
}

private hhmm(time, fmt = "h:mm a")
{
	def t = timeToday(time, location.timeZone)
	def f = new java.text.SimpleDateFormat(fmt)
	f.setTimeZone(location.timeZone ?: timeZone(time))
	f.format(t)
}

private hideOptionsSection() {
	(starting || ending || days || modes) ? false : true
}

private timeIntervalLabel() {
	(starting && ending) ? hhmm(starting) + "-" + hhmm(ending, "h:mm a z") : ""
}
