module Domino where

import Data.Char

type Pip = Int
type Bone = (Pip, Pip, Int)
data Loc = Value Pos Int | Stone Pos Bone Int deriving Show
type Pos = Int
type Board = [Loc]

data Tree a = Node a [Tree a] deriving Show

-- constants to test with
board :: Board
board = [Value  0 6, Value  1 6, Value  2 2, Value  3 6, Value  4 5, Value  5 2, Value  6 4, Value  7 1,
         Value  8 1, Value  9 3, Value 10 2, Value 11 0, Value 12 1, Value 13 0, Value 14 3, Value 15 4,
         Value 16 1, Value 17 3, Value 18 2, Value 19 4, Value 20 6, Value 21 6, Value 22 5, Value 23 4,
         Value 24 1, Value 25 0, Value 26 4, Value 27 3, Value 28 2, Value 29 1, Value 30 1, Value 31 2,
         Value 32 5, Value 33 1, Value 34 3, Value 35 6, Value 36 0, Value 37 4, Value 38 5, Value 39 5,
         Value 40 5, Value 41 5, Value 42 4, Value 43 0, Value 44 2, Value 45 6, Value 46 0, Value 47 3,
         Value 48 6, Value 49 0, Value 50 5, Value 51 3, Value 52 4, Value 53 2, Value 54 0, Value 55 3]

bones :: [Bone]
bones = zip3 [0,0,0,0,0,0,0,1,1,1,1,1,1,2,2,2,2,2,3,3,3,3,4,4,4,5,5,6] [0,1,2,3,4,5,6,1,2,3,4,5,6,2,3,4,5,6,3,4,5,6,4,5,6,5,6,6] [1..28]

smallBoard :: Board
smallBoard = [Value 0 0, Value 1 1, Value 2 1,
         Value 3 0, Value 4 2, Value 5 1,
         Value 6 0, Value 7 2, Value 8 2,
         Value 9 1, Value 10 2, Value 11 0]

smallBones :: [Bone]
smallBones = zip3 [0,0,0,1,1,2] [0,1,2,1,2,2] [0..5]

width :: Int
-- width = 3
width = 8

height :: Int
-- height = 4
height = 7
--------------------

--main method
-- it calculates the possible solutions for the given board and stones, and prints it
main :: Board -> [Bone] -> IO()
main b bns = showAllBoards boards
              where
                tree = gametree b bns
                boards = solutions tree

-- creates a tree of possible steps
-- the first bone in the array is tried to be placed on the board, next the second etc..
gametree :: Board -> [Bone] -> Tree Board
gametree b []       = Node b [] -- mag weg zodra 'full' werkt
gametree b (bn:bns) = Node b [gametree b' bns | b' <- moves b bn]

-- retrieves all possible solutions from the tree
solutions :: Tree Board -> [Board]
solutions (Node b []) | full b    = [b]
                       | otherwise = []
solutions (Node b ts) = [b' | ts' <- ts, b' <- solutions ts']

-- print the solutions
showAllBoards :: [Board] -> IO ()
showAllBoards [] = putStr []
showAllBoards (b:bs) = do showBoard b
                          showAllBoards bs

showBoard :: Board -> IO ()
showBoard [] = putStrLn []
showBoard b  = do putStrLn (take (width * 6) (concat mapStones))
                  showBoard (drop width b)
                where
                  mapStones = map showLocation b

showLocation :: Loc -> String
showLocation (Value _ val)          | length value == 5 = value ++ " "
                                    | otherwise         = value
                                       where value = show val ++ "    "
showLocation (Stone _ (_,_,nr) val) | length value == 4 = value ++ "  "
                                    | length value == 5 = value ++ " "
                                    | otherwise         = value
                                       where
                                         value = show val ++ "(" ++ show nr ++ ")"
--------------------
-- creates all the possible boards with the given Bone
moves :: Board -> Bone -> [Board]
moves b bn | noOptions b = []
           | full b      = []
           | otherwise   = [move b bn n1 n2 | (Value n1 val1, Value n2 val2) <- prs, valid bn (val1,val2)]
                            where
                             prs = getPairs b

-- lays the bone on the two given positions on the board
move :: Board -> Bone -> Int -> Int -> Board
move b bn nx ny = layStone b' bn ny
                  where
                    b' = layStone b bn nx

-- lay the stone on the position on the board
layStone :: Board -> Bone -> Int -> Board
layStone b bn n = xs ++ [Stone pos bn val] ++ ys
                   where
                     (xs, (Value pos val):ys) = splitAt n b

-- get all the free neighbours of a given location on the board
neighbs :: Board -> Loc -> [(Loc,Loc)]
neighbs b (Value n _) = freeRight b n ++ freeBelow b n


freeBelow :: Board -> Int -> [(Loc,Loc)]
freeBelow b n = if (isValid below && isFree (b !! below))
               then [((b !! n), (b !! below))]
               else []
                where
                  below = n + width

freeRight :: Board -> Int -> [(Loc,Loc)]
freeRight b n = if (isValid right) && (right `mod` width /= 0 && isFree (b !! right))
               then [((b !! n), (b !! right))]
               else []
                where
                  right = n + 1
--------------------

-- checks whether it is possible to lay a stone on this Loc
isFree :: Loc -> Bool
isFree (Stone _ _ _) = False
isFree (Value _ _)   = True

-- checks if the board is full
full :: Board -> Bool
full [] = True
full b  = not (isFree (b !! 0)) && full (drop 1 b)

-- checks if there are no options left to lay a stone
noOptions :: Board -> Bool
noOptions b = (length (getPairs b)) == 0

-- gives all possibles pairs of free stones on the board
getPairs :: Board -> [(Loc,Loc)]
getPairs b = concat [neighbs b l | l <- b, isFree l]

-- checs whether the stone is located on the board
isValid :: Int -> Bool
isValid n = n < (width * height) && n >= 0

-- TODO: check if bone is not already placed -> isn't needed since you go along the list with al bones
-- checks if the values on the bone are the same as the values on the field
valid :: Bone -> (Int, Int) -> Bool
valid (p1,p2,_) (n1,n2) = (p1 == n1 && p2 == n2) || (p1 == n2 && p2 == n1)
