import { Prop, Schema, SchemaFactory } from '@nestjs/mongoose';
import { Document } from 'mongoose';

@Schema()
export class StationAvailablityDocument extends Document {
    @Prop()
    station_id: number;

    @Prop()
    stationCode: string;
    
    @Prop()
    name: string;

    @Prop()
    lon: number;

    @Prop()
    lat: number;

    @Prop()
    mechanical: number;

    @Prop()
    ebike: number;

    @Prop()
    numDocksAvailable: number;

    @Prop()
    last_reported: number;

    @Prop()
    lastUpdatedOther: number;
}

export const StationAvailablitySchema = SchemaFactory.createForClass(StationAvailablityDocument);
