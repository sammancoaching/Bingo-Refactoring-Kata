# frozen_string_literal: true

class BingoBoard
  class IllegalStateException < StandardError; end

  def initialize(width, height)
    @cells = Array.new(width) { Array.new(height) }
    @marked = Array.new(width) { Array.new(height, false) }
  end

  def define_cell(x, y, value)
    if @cells[x][y]
      raise IllegalStateException, 'Cell already defined.'
    end

    @cells.each_with_index do |row, c|
      row.each_with_index do |cell, r|
        if value == cell
          raise IllegalStateException, "#{value} already present at #{c}, #{r}"
        end
      end
    end

    @cells[x][y] = value
  end

  def mark_cell(x, y)
    unless initialized?
      raise IllegalStateException, 'Board is not initialized.'
    end

    @marked[x][y] = true
  end

  def initialized?
    @cells.all? { |row| row.all? { |cell| !cell.nil? } }
  end

  def marked?(x, y)
    @marked[x][y]
  end
end