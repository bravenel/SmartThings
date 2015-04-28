There are two device types that may be of value:

"Dimmer Switch with Off" is the standard "Dimmer Switch" device type with one additional command:  resetLevel(value)
This can be used to change the dimmer level on the switch, and then immediately turning the switch off.  Dimmers come
on to their last value -- this command changes that last value so that the next time the switch is turned on, it
comes on at the desired dimmer level.

"Aeon Motor Controller" is a new device type for the Aeon Micro Motor Controller (DSC-14104 ZWUS).  It provides
the three commands the device is capable of, Up, Down, and Stop.  There is a ReadMe for the device type with details.
