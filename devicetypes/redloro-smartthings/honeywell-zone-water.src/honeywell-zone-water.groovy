/**
 *  SmartThings Device Handler: Honeywell Zone Water
 *
 *  Author: hicksbr@gmail.com
 *
 *  Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 *  in compliance with the License. You may obtain a copy of the License at:
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software distributed under the License is distributed
 *  on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License
 *  for the specific language governing permissions and limitations under the License.
 */
metadata {
  definition (name: "Honeywell Zone Water", namespace: "redloro-smartthings", author: "hicksbr@gmail.com") {
    capability "Water Sensor"
    capability "Sensor"

    command "zone"
  }

  tiles(scale: 2) {
    multiAttributeTile(name:"zone", type: "generic", width: 6, height: 4){
      tileAttribute ("device.water", key: "PRIMARY_CONTROL") {
        attributeState "dry", label:"DRY", icon:"st.alarm.water.dry", backgroundColor:"#ffffff"
        attributeState "wet", label:"WET", icon:"st.alarm.water.wet", backgroundColor:"#53a7c0"
      }
    }

    main "zone"

    details(["zone"])
  }
}

def zone(String state) {
  // need to convert open to detected and closed to clear
  def eventMap = [
    'closed':"dry",
    'open':"wet",
	'alarm':"wet"
  ]
  def newState = eventMap."${state}"

  def descMap = [
    'closed':"Was Dry",
    'open':"Was Wet",
    'alarm':"Was Wet"
  ]
  def desc = descMap."${state}"

  sendEvent (name: "water", value: "${newState}", descriptionText: "${desc}")
}