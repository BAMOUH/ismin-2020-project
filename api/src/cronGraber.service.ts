import { Injectable, Logger ,HttpService} from '@nestjs/common';
import { InjectModel } from '@nestjs/mongoose';
import { Cron } from '@nestjs/schedule';
import { Model } from 'mongoose';
import { StationAvailablityDocument } from './Station.schema';
import { StationAvailability } from './StationAvailability';
import { map } from 'rxjs/operators';


@Injectable()
export class cronGraberService {
  
  private readonly logger = new Logger(cronGraberService.name);
  constructor(
    @InjectModel(StationAvailablityDocument.name) private StationAvailabilityModel: Model<StationAvailablityDocument>,
    private http: HttpService,
  ) {}

  @Cron('30 * * * * *')
  async handleCronNameLatLon() {
    this.logger.debug('Refetching NameLatLon data...');

    try {
      let data  = await this.http.get(process.env.STATION_NAME_LAT_LON_API_URL).pipe(
        map(response => response.data),
      );
      let response = data.toPromise();
      response.then(data =>{
        let StationEntities : Array<any> = data.data.stations;
        
        StationEntities.map(oneStationEntity => this.handleUpdateOrSendNameLatLon(oneStationEntity));
      }).catch(
        error => this.logger.debug("Error" +error)
      );
    } catch (error) {
      this.logger.debug("Error" +error);
      
    }
    await this.logger.debug('Done refetching NameLatLon data !');
   
  }

  async handleUpdateOrSendNameLatLon(oneStationEntity  ){
    let stationNameLonLat ={
      station_id: oneStationEntity.station_id,
      name: oneStationEntity.name,
      lon: oneStationEntity.lon,
      lat: oneStationEntity.last_reported,
    } 
    
    let query = { 
      station_id: stationNameLonLat.station_id
    };
    let update = stationNameLonLat;
    let options = {upsert: true, new: true, setDefaultsOnInsert: true};
    await this.StationAvailabilityModel.findOneAndUpdate(query, update, options);
  }


  @Cron('45 * * * * *')
  async handleCronStationAvailability() {
    this.logger.debug('Refetching data...');

    try {
      let data  = await this.http.get(process.env.STATION_AVAILABILITY_API_URL).pipe(
        map(response => response.data),
      );
      let response = data.toPromise();
      response.then(data =>{
        let StationEntities : Array<any> = data.data.stations;
        
        StationEntities.map(oneStationEntity => this.handleUpdateOrSendStationAvailability(oneStationEntity,data.lastUpdatedOther));
      }).catch(
        error => this.logger.debug("Error" +error)
      );
    } catch (error) {
      this.logger.debug("Error" +error);
      
    }
    await this.logger.debug('Done refetching data !');
   
  }

  async handleUpdateOrSendStationAvailability(oneStationEntity , lastUpdatedOther : number){
    let stationAv : StationAvailability={
      station_id: oneStationEntity.station_id,
      stationCode: oneStationEntity.stationCode,
      mechanical: oneStationEntity.num_bikes_available_types[0].mechanical,
      ebike: oneStationEntity.num_bikes_available_types[1].ebike,
      numDocksAvailable: oneStationEntity.numDocksAvailable,
      last_reported: oneStationEntity.last_reported,
      lastUpdatedOther: lastUpdatedOther,
    } 
    
    let query = { 
      station_id: stationAv.station_id
    };
    let update = stationAv;
    let options = {upsert: true, new: true, setDefaultsOnInsert: true};
    await this.StationAvailabilityModel.findOneAndUpdate(query, update, options);
  }
}