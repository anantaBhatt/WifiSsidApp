import {
  NativeModules,
  Platform,
  PermissionsAndroid,
} from 'react-native';

const { WifiModule } = NativeModules;

async function ensureLocationPermission(): Promise<void> {
  if (Platform.OS !== 'android') return;

  const granted = await PermissionsAndroid.request(
    PermissionsAndroid.PERMISSIONS.ACCESS_FINE_LOCATION,
    {
      title: 'Location Permission',
      message: 'We need location permission to read your WiFi SSID.',
      buttonPositive: 'OK',
    },
  );

  if (granted !== PermissionsAndroid.RESULTS.GRANTED) {
    throw new Error('Location permission denied');
  }
}

export async function getCurrentSsid(): Promise<string> {
  await ensureLocationPermission();
  const ssid: string = await WifiModule.getSsid();
  return ssid;
}
