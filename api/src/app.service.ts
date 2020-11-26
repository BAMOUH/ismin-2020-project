import { Injectable } from '@nestjs/common';
import { InjectModel } from '@nestjs/mongoose';
import { Model } from 'mongoose';
import { StationAvailablityDocument } from './Station.schema';
import { StationInfos } from './StationInfos';

@Injectable()
export class AppService {

  constructor(
    @InjectModel(StationAvailablityDocument.name) private StationsModel: Model<StationAvailablityDocument>,
  ) {}

  async getStations(): Promise<StationInfos[]>  {
    return (await this.StationsModel.find().exec())
    .map(
      (stationDocObj: StationAvailablityDocument): StationInfos => {
        return {
          station_id: stationDocObj.station_id,
          stationCode: stationDocObj.stationCode,
          name: stationDocObj.name,
          lat: stationDocObj.lat,
          lon:stationDocObj.lon,
          mechanical: stationDocObj.mechanical,
          ebike: stationDocObj.ebike,
          numDocksAvailable: stationDocObj.numDocksAvailable,
          last_reported: stationDocObj.last_reported,
          lastUpdatedOther: stationDocObj.lastUpdatedOther,
        };
      },
    )
  }

  async getStationById(stationId : number): Promise<StationInfos>   {
    
    const firstStationWithId: StationAvailablityDocument[] = await this.StationsModel.find({
      station_id: stationId,
    }).exec();
    if (firstStationWithId.length !== 0) {
      return {
        station_id: firstStationWithId[0].station_id,
        stationCode: firstStationWithId[0].stationCode,
        name: firstStationWithId[0].name,
        lat: firstStationWithId[0].lat,
        lon:firstStationWithId[0].lon,
        mechanical: firstStationWithId[0].mechanical,
        ebike: firstStationWithId[0].ebike,
        numDocksAvailable: firstStationWithId[0].numDocksAvailable,
        last_reported: firstStationWithId[0].last_reported,
        lastUpdatedOther: firstStationWithId[0].lastUpdatedOther,
      };
      
    }
  }
}
