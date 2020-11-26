export interface StationAvailability {
    station_id: number;
    stationCode: string;
    mechanical: number;
    ebike: number;
    numDocksAvailable: number;
    last_reported: number;
    lastUpdatedOther: number;
  }
  