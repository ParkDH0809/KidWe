import type {Meta, StoryObj} from '@storybook/react';
import MoreButton from '@/components/molecules/DropdownButton/MoreButton';

const meta: Meta<typeof MoreButton> = {
  component: MoreButton,
};

export default meta;

type Story = StoryObj<typeof MoreButton>;
export const Default: Story = {
  render: function () {
    return <MoreButton position="right"></MoreButton>;
  },
};
