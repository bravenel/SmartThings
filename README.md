There are two device types that may be of value:

"Dimmer Switch with Reset" is the standard "Dimmer Switch" device type with one additional command:  resetLevel(value)

This can be used to change the dimmer level on the switch, and then immediately turning the switch off.  Dimmers come
on to their last value -- this command changes that last value so that the next time the switch is turned on, it
comes on at the desired dimmer level.

"Aeon Motor Controller" is a new device type for the Aeon Micro Motor Controller (DSC-14104 ZWUS).  It provides
the three commands the device is capable of, Up, Down, and Stop.  There is a ReadMe for the device type with details.

There are some apps that you may find useful, perhaps for coding examples:

"Scene Controller" is a modified version of the old ST app, Button Controller.  It is used to setup an Aeon Minimote.
This extended version allows additional control, including: on, off and toggle for lights; two different dimmer levels for dimmers; a ceiling fan one-button adjustment; a one button control of the Aeon Motor Controller; changing the mode; running a Hello home phrase; and linking a momentary button for any Minimote button.

"Mode Lighting" is a lighting controller that sets dimmer levels based on mode, incorporating motion on/off logic, as well as master-switch capability for both on and off.  It supports a momentary button for triggering from a Minimote or Hello Home phrase.  It has its own ReadMe with details.  This app relies on the "Dimmer Switch with Off" device type.
