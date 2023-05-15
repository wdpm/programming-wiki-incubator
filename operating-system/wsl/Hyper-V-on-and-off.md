# Turn on/off Hyper-V

ON
```bash
Enable-WindowsOptionalFeature -Online -FeatureName Microsoft-Hyper-V -All
```
OFF
```bash
Disable-WindowsOptionalFeature -Online -FeatureName Microsoft-Hyper-V-All
```