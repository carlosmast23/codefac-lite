; Script generated by the Inno Setup Script Wizard.
; SEE THE DOCUMENTATION FOR DETAILS ON CREATING INNO SETUP SCRIPT FILES!

#define MyAppName "Codefac"
#define MyAppVersion "1.2.8.7.5"
#define MyAppPublisher "Codesoft"
#define MyAppURL "http://www.cf.codesoft-ec.com/"
#define MyAppExeName "codefac.bat"

[Setup]
; NOTE: The value of AppId uniquely identifies this application.
; Do not use the same AppId value in installers for other applications.
; (To generate a new GUID, click Tools | Generate GUID inside the IDE.)
AppId={{98C1B091-C86C-4E82-9E4F-1187B63F5821}
AppName={#MyAppName}
AppVersion={#MyAppVersion}
;AppVerName={#MyAppName} {#MyAppVersion}
AppPublisher={#MyAppPublisher}
AppPublisherURL={#MyAppURL}
AppSupportURL={#MyAppURL}
AppUpdatesURL={#MyAppURL}
DefaultDirName={%USERPROFILE}\{#MyAppName}
DisableDirPage=yes
DisableProgramGroupPage=yes
OutputBaseFilename=codefac_setup
Compression=lzma
SolidCompression=yes

[Languages]
Name: "spanish"; MessagesFile: "compiler:Languages\Spanish.isl"

[Tasks]
Name: "desktopicon"; Description: "{cm:CreateDesktopIcon}"; GroupDescription: "{cm:AdditionalIcons}"; Flags: unchecked

[Files]
Source: "prerequisitos/jre-8u251-windows-x64.exe"; DestDir: "{tmp}"; Flags: deleteafterinstall

Source: "recursos/codefac.bat"; DestDir: "{app}"; Flags: ignoreversion
Source: "recursos/icono_codefac.ico"; DestDir: "{app}"; Flags: ignoreversion
Source: "jar/codefac.jar"; DestDir: "{app}"; Flags: ignoreversion
Source: "lib/*"; DestDir: "{app}/lib"; Flags: ignoreversion
; NOTE: Don't use "Flags: ignoreversion" on any shared system files

[Icons]
Name: "{commonprograms}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}"
Name: "{commondesktop}\{#MyAppName}"; Filename: "{app}\{#MyAppExeName}";IconFilename: "{app}\icono_codefac.ico"; Tasks: desktopicon

[Run]
Filename: "{tmp}\jre-8u251-windows-x64.exe"; StatusMsg: Installing JRE 8...

Filename: "{app}\{#MyAppExeName}"; Description: "{cm:LaunchProgram,{#StringChange(MyAppName, '&', '&&')}}"; Flags: shellexec postinstall skipifsilent

