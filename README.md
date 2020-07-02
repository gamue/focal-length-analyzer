# Focal Length Analyzer

Simple and quick project to get the focal length of images in a directory and it's subdirectories.
Background was to check which focal length I most commonly work with and use this data point in finding a good suited lens for my travels.

## Usage

### IDE
Set values in `CliConfig` and execute the `ApplicationRunner`-class. 

### Compilation
You can use a compiled version of this project via command line by executing:
```
java -jar focal-length-analyzer-1.0.jar -d DIRECTORY_TO_SCAN [-o OUTPUT_FILE]
```

#### -d
Path to directory which should be scanned. All sub-directories will be scanned as well.
- Example: `/usr/tmp/pictures`
- Default value: *none*

#### -o
(optional) Output file. If no output file is defined the result will be written to the console
- Example: `result.csv`
- Default value: *none*

#### -s
(optional) Boolean flag if the focal lengths should be split by cameras. 
This option is useful if you're using multiple cameras but you're only interested in the files of one of them or want 
to compare the usage of the different cameras.
- Example: `true`
- Default value: `false`

#### -ff
(optional) Boolean flag to define if the focal lengths should be in 35mm film equivalent, by default they are not and
therefor ignore the crop factor of your camera. 
This option is useful in case you want to compare focal lengths at a camera with a crop factor (e.g. APS-C) 
with the focal lengths of a full-frame one.
- Example: `true`
- Default value: `false`
