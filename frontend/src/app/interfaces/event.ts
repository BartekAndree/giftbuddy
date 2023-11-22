export interface EventResponseDTO {
  id: string;
  organizerId: string;
  title: string;
  description?: string;
  giftIdea?: string;
  imageUrl?: string;
  contribution?: number;
  currentAmount?: number;
  targetAmount?: number;
  eventDate?: Date;
  endDate: Date;
  isActive: boolean;
}

export interface EventRequestDTO {
  organizerId: string;
  title: string;
  description?: string;
  giftIdea?: string;
  imageUrl?: string;
  contribution?: number;
  targetAmount?: number;
  eventDate?: Date;
  endDate: Date;
}

