# Master Slave Dashboard Creator

## About Utility
GUI based tool helps build Master / Slave dashboards. A user selects a master dashlet and the rest are automatically turned into slaves of this dashboard. Great for business-oriented dashboards.

## Usage
Download the [latest release](https://github.com/Dynatrace/Master-Slave-Dashboard-Creator/releases) and double click to open the JAR file.
This will launch the GUI window.

1. Click the button to select the dashboard you wish to process.
2. Select the name of the dashlet you wish to use as the master.
3. Click *Process Dashboard*
4. A new dashboard will be created and placed in the same folder as the original dashboard. This new dashboard will have *_master_slave.dashboard.xml* appended to the dashboard file.
5. Open your new * *_master_slave.dashboard.xml* in AppMon

Problems, issues or questions? Please post on the the [Dynatrace Community Forum](https://answers.dynatrace.com/spaces/148/index.html) or contact the author: adam.gardner@dynatrace.com
